package com.study.spring.oauth.oauth;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.Map;

public class MyUserDetailsByNameServiceWrapper<T extends Authentication> implements
        AuthenticationUserDetailsService<T>, InitializingBean {
    private UserDetailsService userDetailsService = null;

    /**
     * Constructs an empty wrapper for compatibility with Spring Security 2.0.x's method
     * of using a setter.
     */
    public MyUserDetailsByNameServiceWrapper() {
        // constructor for backwards compatibility with 2.0
    }

    /**
     * Constructs a new wrapper using the supplied
     * {@link UserDetailsService} as the
     * service to delegate to.
     *
     * @param userDetailsService the UserDetailsService to delegate to.
     */
    public MyUserDetailsByNameServiceWrapper(final UserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null.");
        this.userDetailsService = userDetailsService;
    }

    /**
     * Check whether all required properties have been set.
     *
     * @see InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "UserDetailsService must be set");
    }

    /**
     * Get the UserDetails object from the wrapped UserDetailsService implementation
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication.getPrincipal();
        Map<String, String> map = (Map<String, String>) usernamePasswordAuthenticationToken.getDetails();
        JSONObject jsonObject = new JSONObject();
        try {
            for(Map.Entry<String, String> entry : map.entrySet()){
                if("password".equals(entry.getKey())){
                    jsonObject.put("mypassword", entry.getValue());
                }else{
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return this.userDetailsService.loadUserByUsername(jsonObject.toString());
    }

    /**
     * Set the wrapped UserDetailsService implementation
     *
     * @param aUserDetailsService The wrapped UserDetailsService to set
     */
    public void setUserDetailsService(UserDetailsService aUserDetailsService) {
        this.userDetailsService = aUserDetailsService;
    }
}