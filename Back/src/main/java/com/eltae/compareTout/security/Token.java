package com.eltae.compareTout.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Token {
    public String generateRandomToken(){
        return UUID.randomUUID().toString();
    }
}
