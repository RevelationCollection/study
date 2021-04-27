package com.study.spring.oauth.oauth;

import com.study.spring.oauth.util.AesCoder;
import com.study.spring.oauth.util.RsaCoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("myTokenEnhancer")
public class MyTokenEnhancer implements TokenEnhancer {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 生成AES的key，交给客户端访问资源加密使用
	 * @param accessToken
	 * @param authentication
	 * @return
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInformation = new HashMap<>(1);
		try {
			Map<String, String> params = authentication.getOAuth2Request().getRequestParameters();
			String aesKey = null;
			// 客户端传过来的aes密钥不为空，则优先使用客户端生成的密钥，并且不再回传回去
			if (params.get("fault") != null) {
				// logger.info("client fault:{}", params.get("fault"));
				String rsaPrivateKey = null ; //(String) RequestContext.getCurrentContext().get("rsaPrivate");
				aesKey = RsaCoder.decryptByPrivateKey((String) params.get("fault"), rsaPrivateKey);
				additionalInformation.remove("fault");
			}else{
				// 客户端未传密钥，则使用随即生成的密钥，并回传客户端
				aesKey = AesCoder.genKeyAES();
				additionalInformation.put("fault", aesKey);
			}
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

			// readAccessToken时会使用
			long accessTokenExpiration = -1L;
			DefaultOAuth2AccessToken expiringAccessToken = null;
			if (accessToken instanceof DefaultOAuth2AccessToken) {
				expiringAccessToken = (DefaultOAuth2AccessToken) accessToken;
				accessTokenExpiration = (expiringAccessToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
			}
			String encAccessToken = AesCoder.encryptData(aesKey, accessToken.getValue());
			// aesKey信息和token有效期一致，为了避免错误，多加10分钟(600)
			stringRedisTemplate.opsForValue().set(encAccessToken, aesKey, accessTokenExpiration + 600, TimeUnit.SECONDS);
			log.info("my token enhancer set aesKey:{} accessToken:{} expiration:{}", aesKey, encAccessToken, accessTokenExpiration + 600);

			// readRefreshToken时会使用
			long refreshTokenExpiration = -1L;
			if (accessToken.getRefreshToken() instanceof DefaultExpiringOAuth2RefreshToken) {
				DefaultExpiringOAuth2RefreshToken expiringRefreshToken = (DefaultExpiringOAuth2RefreshToken) accessToken.getRefreshToken();
				refreshTokenExpiration = (expiringRefreshToken.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
			}
			String encRefreshToken = AesCoder.encryptData(aesKey, accessToken.getRefreshToken().getValue());
			stringRedisTemplate.opsForValue().set(encRefreshToken, aesKey, refreshTokenExpiration + 600, TimeUnit.SECONDS);
			log.info("my token enhancer set aesKey:{} refreshToken:{} expiration:{}", aesKey, encRefreshToken, refreshTokenExpiration + 600);
		} catch (Exception e) {
			log.error("my token enhancer enhance accessToken error", accessToken.getValue(), e);
			throw new IllegalStateException("Cannot gen key aes", e);
		}
		return accessToken;
	}
}