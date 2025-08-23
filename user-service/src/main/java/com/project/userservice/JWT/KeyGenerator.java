package com.project.userservice.JWT;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // strong key for HS512
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Paste this into application.properties:");
        System.out.println(base64Key);
    }
}
