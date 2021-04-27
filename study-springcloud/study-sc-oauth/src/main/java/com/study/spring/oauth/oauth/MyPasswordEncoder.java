package com.study.spring.oauth.oauth;

import com.study.spring.gateway.config.RsaProperties;
import com.study.spring.oauth.util.RsaCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	@Autowired
	private RsaProperties rsaProperties;

	/**
	 * 用RSA公钥加密
	 * @param rawPassword
	 * @return
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			if (USER_NOT_FOUND_PASSWORD.equals(rawPassword)) {
				return USER_NOT_FOUND_PASSWORD;
			}
			String rsaPrivateKey = null ;//(String) RequestContext.getCurrentContext().get("rsaPrivate");
			logger.debug("my pasword encoder encode rawPassword:{} rsaPrivateKey:{}", rawPassword, rsaPrivateKey);
			return RsaCoder.encryptByPublicKey(rawPassword.toString(), rsaPrivateKey);
		} catch (Exception e) {
			throw new IllegalArgumentException("Bad password");
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// 原密码，客户端传递过来，直接用密文即可，加密后的密码这是根据用户名查询到的密码，也是加密的，比对二者一致就说明是认证通过
		String newRawPassword = null;
		try {
			String rsaPrivateKey = null;//(String) RequestContext.getCurrentContext().get("rsaPrivate");
			logger.debug("my pasword encoder encode rawPassword:{} encodedPassword:{} rsaPrivateKey:{}", rawPassword, encodedPassword, rsaPrivateKey);
			newRawPassword = RsaCoder.decryptByPrivateKey(rawPassword.toString(), rsaPrivateKey);
		} catch (Exception e) {
			logger.error("matches:{}", e);
			throw new IllegalArgumentException("Bad password", e);
		}
		return newRawPassword.equals(encodedPassword);
	}
}