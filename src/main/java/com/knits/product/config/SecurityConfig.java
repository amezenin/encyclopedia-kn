package com.knits.product.config;

import com.knits.product.security.jwt.JwtConfigurer;
import com.knits.product.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ENDPOINT = "/api/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String UNAUTHORIZED_ENDPOINT = "/api/articles/**";
    private static final String GET_ARTICAL_WITHOUT_LOGIN = "/api/articles/{id}";
    private static final String GET_COMMENTS = "/api/comments/**";
    private static final String GET_USERS_WITHOUT_LOGIN = "/api/users/**";
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String ROLES = "/api/roles/**";

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT,
                        UNAUTHORIZED_ENDPOINT,
                        GET_ARTICAL_WITHOUT_LOGIN,
                        GET_USERS_WITHOUT_LOGIN,
                        REGISTER_ENDPOINT,
                        ROLES,
                        GET_COMMENTS
                        ).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/error/403")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }


}
