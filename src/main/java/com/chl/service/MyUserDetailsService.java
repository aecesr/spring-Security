package com.chl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: hello-security
 * @description:
 * @Author: 曹红亮
 * @create: 2022-04-21 21:14
 **/
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 可以从任何地方获取数据
     *
     * @param username username
     * @return UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        log.info("获取到的用户信息：" + username);
        String password = passwordEncoder.encode("123456");
        // 写死一个密码，赋予一个 admin 权限【正常应该是在数据库中查询出来】
        return new User(username, password,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
