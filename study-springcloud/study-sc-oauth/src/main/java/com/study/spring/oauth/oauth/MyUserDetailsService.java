package com.study.spring.oauth.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 自定义用户数据
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 请求后端服务，根据一个key找到请求的地址--->自定义AuthenticationProvider--->自定义AuthenticationManager，从而控制key到路径映射，是否能把key加到username这个参数里来 username,key
        // 按照逗号分隔，key去数据库找到对应的登录url，username作为参数请求过去，拿到密码，自定义加密后，比对密文
        // 另一个自定义密码处理类，加密比对两个密文
        logger.info("my userDetail manager load username:{}", userName);
        // 根据client_id查询对应RSA秘钥
        // 找不到使用默认RSA私钥(TODO: 满足当前client_id用RSA加密的情况，过渡期)

        // 这里用户具备的属性，仿照InMemoryUserDetailsManager，后续需要管理上 username
        Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("oauth2");
        return new User(userName, userName, true, true, true, true,
                grantedAuthorities);
    }
}
