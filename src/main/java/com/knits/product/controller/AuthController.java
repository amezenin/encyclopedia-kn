package com.knits.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {



    @GetMapping(value = "/login")
    public String getLoginPage(){
        return "login"; //Circular View Path Error https://www.baeldung.com/spring-circular-view-path-error
    }

    @GetMapping(value = "/success")
    public String getSuccessPage(){
        return "success";
    }
}
