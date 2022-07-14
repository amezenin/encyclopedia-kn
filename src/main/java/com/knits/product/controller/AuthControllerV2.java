package com.knits.product.controller;

import com.knits.product.entity.User;
import com.knits.product.repository.UserRepository;
import com.knits.product.securityV2.jwt.JwtTokenFilter;
import com.knits.product.securityV2.jwt.JwtTokenProvider;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.AuthenticationRequestDTO;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.RoleMapper;
import com.knits.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerV2 {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDTO requestDTO){
        try {
            String login = requestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDTO.getPassword()));
            User user = userService.findByLogin(login);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + login + " not found");
            }

            String token = jwtTokenProvider.createToken(login, user.getRoleList());

            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }


}
