package com.knits.product.controller;

import com.knits.product.entity.User;
import com.knits.product.repository.UserRepository;
import com.knits.product.security.JwtTokenProvider;
import com.knits.product.service.dto.AuthenticationRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthJWTController {

    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthJWTController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getLogin(), requestDTO.getPassword()));
            User user = userRepository.findOneByLogin(requestDTO.getLogin())
                    .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
            String token = jwtTokenProvider.createToken(requestDTO.getLogin(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", requestDTO.getLogin());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid login/password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response,null);
    }

    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "login"; //Circular View Path Error https://www.baeldung.com/spring-circular-view-path-error
    }
}
