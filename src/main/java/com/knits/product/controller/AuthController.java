package com.knits.product.controller;

import com.knits.product.entity.User;
import com.knits.product.exceptions.UserException;
import com.knits.product.security.jwt.JwtTokenFilter;
import com.knits.product.security.jwt.JwtTokenProvider;
import com.knits.product.service.RoleService;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.AuthenticationRequestDTO;
import com.knits.product.service.dto.RoleDTO;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/login")
    @CrossOrigin(maxAge = 360)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO requestDTO){
        try {
            String login = requestDTO.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDTO.getPassword()));
            User user = userService.findByLogin(login);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + login + " not found");
            }

            String token = jwtTokenProvider.createToken(login, user.getRoles());

            List<String> roles = jwtTokenProvider.getRoleNames(user.getRoles());

            Map<Object, Object> response = new HashMap<>(); // specify the type was <Object, Object>
            response.put("roles", roles); //its wrong, beceause we have roles in token. for testing
            //response.put("tokenType", "Bearer");
            response.put("token", token);
            response.put("id", user.getId());
            response.put("login", login);
            response.put("email", user.getEmail());



            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password");
        }
    }

    @PostMapping(value = "/register", produces = {"application/json"}, consumes = { "application/json"})
    @CrossOrigin
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            throw new UserException("User data are missing");
        }
        return ResponseEntity
                .ok()
                .body(userService.save(userDTO));
    }



}
