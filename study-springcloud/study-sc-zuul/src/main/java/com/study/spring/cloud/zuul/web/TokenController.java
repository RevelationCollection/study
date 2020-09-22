package com.study.spring.cloud.zuul.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @PostMapping("/token/login")
    public ResponseEntity<String> login(String phone,String password){

        return null;
    }
}
