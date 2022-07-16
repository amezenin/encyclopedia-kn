package com.knits.product.security;

import com.knits.product.entity.User;
import com.knits.product.security.jwt.JwtUser;
import com.knits.product.security.jwt.JwtUserFactory;
import com.knits.product.service.UserService;
import com.knits.product.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {

        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername(JWTUserDetailsService) - user with username: {} successfully loaded", login);
        return jwtUser;
    }
}
