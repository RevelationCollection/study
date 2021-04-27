package com.study.spring.oauth.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

public class MyPreAuthenticatedAuthenticationProvider implements AuthenticationProvider,
        InitializingBean, Ordered {

    private static final Logger logger = LoggerFactory
            .getLogger(PreAuthenticatedAuthenticationProvider.class);

    private AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> preAuthenticatedUserDetailsService = null;
    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private boolean throwExceptionWhenTokenRejected = false;

    private int order = -1; // default: same as non-ordered

    /**
     * Check whether all required properties have been set.
     */
    @Override
    public void afterPropertiesSet() {
        Assert.notNull(preAuthenticatedUserDetailsService,
                "An AuthenticationUserDetailsService must be set");
    }

    /**
     * Authenticate the given PreAuthenticatedAuthenticationToken.
     * <p>
     * If the principal contained in the authentication object is null, the request will
     * be ignored to allow other providers to authenticate it.
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }

        if (authentication.getPrincipal() == null) {
            logger.debug("No pre-authenticated principal found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException(
                        "No pre-authenticated principal found in request.");
            }
            return null;
        }

        if (authentication.getCredentials() == null) {
            logger.debug("No pre-authenticated credentials found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException(
                        "No pre-authenticated credentials found in request.");
            }
            return null;
        }

        UserDetails ud = preAuthenticatedUserDetailsService
                .loadUserDetails((PreAuthenticatedAuthenticationToken) authentication);

        userDetailsChecker.check(ud);

        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(
                ud, authentication.getCredentials(), ud.getAuthorities());
        result.setDetails(authentication.getDetails());

        return result;
    }

    /**
     * Indicate that this provider only supports PreAuthenticatedAuthenticationToken
     * (sub)classes.
     */
    @Override
    public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Set the AuthenticatedUserDetailsService to be used to load the {@code UserDetails}
     * for the authenticated user.
     *
     * @param uds
     */
    public void setPreAuthenticatedUserDetailsService(
            AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> uds) {
        this.preAuthenticatedUserDetailsService = uds;
    }

    /**
     * If true, causes the provider to throw a BadCredentialsException if the presented
     * authentication request is invalid (contains a null principal or credentials).
     * Otherwise it will just return null. Defaults to false.
     */
    public void setThrowExceptionWhenTokenRejected(boolean throwExceptionWhenTokenRejected) {
        this.throwExceptionWhenTokenRejected = throwExceptionWhenTokenRejected;
    }

    /**
     * Sets the strategy which will be used to validate the loaded <tt>UserDetails</tt>
     * object for the user. Defaults to an {@link AccountStatusUserDetailsChecker}.
     * @param userDetailsChecker
     */
    public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
        Assert.notNull(userDetailsChecker, "userDetailsChecker cannot be null");
        this.userDetailsChecker = userDetailsChecker;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int i) {
        order = i;
    }
}