package com.study.spring.oauth.oauth;

import com.study.spring.oauth.util.RsaCoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
public class OuterServiceDefaultTokenServices extends MyDefaultTokenServices {

    @Override
    @Transactional(noRollbackFor={InvalidTokenException.class, InvalidGrantException.class})
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
            throws AuthenticationException {

        if (!supportRefreshToken) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }

        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
        if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        }
        OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(refreshToken);
        String clientSecret = null;
        if(authentication.getOAuth2Request().getExtensions() != null){
            clientSecret = (String) authentication.getOAuth2Request().getExtensions().get("client_secret");
        }
        // 请求传递为密文
        String requestClientSecret = null;
        if(tokenRequest.getRequestParameters() != null){
            requestClientSecret = tokenRequest.getRequestParameters().get("client_secret");
        }

        String rsaPrivateKey = null;
        try {
            rsaPrivateKey = null;// (String) RequestContext.getCurrentContext().get("rsaPrivate");
            log.debug("my default token services get threadlocal rsaPrivateKey:{}", rsaPrivateKey);
        } catch (Exception e) {
            throw new InvalidGrantException("Wrong data for this refresh token: " + refreshTokenValue);
        }
        String rawClientSecret = null;
        try {
            rawClientSecret = RsaCoder.decryptByPrivateKey(requestClientSecret, rsaPrivateKey);
            log.debug("my default token services get authentication requestClientSecret:{} rawClientSecret:{}", requestClientSecret, rawClientSecret);
        } catch (Exception e) {
            throw new InvalidGrantException("Wrong auth clientId for this refresh token: " + refreshTokenValue);
        }

        if (clientSecret == null || !clientSecret.equals(rawClientSecret)) {
            throw new InvalidGrantException("Wrong client for this refresh token: " + refreshTokenValue);
        }

        // refresh token未过期，则延长过期时间
        boolean isRefreshTokenExpired = false;
        if (!isExpired(refreshToken)) {
            refreshToken = updateRefreshExpiration(authentication, refreshToken);
            // 更新refresh: refresh_auth: refresh_to_access: access_to_refresh:时间
            tokenStore.storeRefreshToken(refreshToken, authentication);
            // 将更新的value还原回去，但是保留过期时间
            if(refreshToken instanceof DefaultExpiringOAuth2RefreshToken &&
                    refreshToken.getValue().startsWith("{") &&
                    refreshToken.getValue().endsWith("}")){
                DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken)refreshToken;
                String specialValue = defaultExpiringOAuth2RefreshToken.getValue();
                specialValue = specialValue.substring(1, specialValue.length() - 1);
                refreshToken = new DefaultExpiringOAuth2RefreshToken(specialValue, defaultExpiringOAuth2RefreshToken.getExpiration());
            }
        } else{
            isRefreshTokenExpired = true;
        }

        // clear out any access tokens already associated with the refresh token.
        // 通过refresh token查找到access token，设置其有效期为指定的过渡期时间
        OAuth2RefreshToken newRefreshToken = updateRefreshValue(refreshToken);
        tokenStore.removeAccessTokenUsingRefreshToken(newRefreshToken);

        // refresh token过期，则清理掉
        if (isRefreshTokenExpired) {
            tokenStore.removeRefreshToken(refreshToken);
            throw new InvalidTokenException("Invalid refresh token (expired): " + refreshToken);
        }

        authentication = createRefreshedAuthentication(authentication, tokenRequest);

        if (!reuseRefreshToken) {
            tokenStore.removeRefreshToken(refreshToken);
            refreshToken = createRefreshToken(authentication);
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        if (!reuseRefreshToken) {
            tokenStore.storeRefreshToken(accessToken.getRefreshToken(), authentication);
        }
        return accessToken;
    }

    private OAuth2RefreshToken updateRefreshValue(OAuth2RefreshToken refreshToken){
        String value = refreshToken.getValue();
        // 因为TokenStore未提供设置accessToken的方法，这里增加()标识，来表示需要更新access_token相关有效期
        if(value.startsWith("{") && value.endsWith("}")){
            value = value.substring(1, value.length() - 1);
        }
        value = "(" + value + ")";
        if(refreshToken instanceof DefaultExpiringOAuth2RefreshToken){
            Date expiration = ((DefaultExpiringOAuth2RefreshToken) refreshToken).getExpiration();
            return new DefaultExpiringOAuth2RefreshToken(value, expiration);
        }
        return new DefaultOAuth2RefreshToken(value);
    }
}