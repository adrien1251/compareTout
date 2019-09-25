package com.eltae.compareTout.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hashing {

    public static String sha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte h : hash) {
                String hex = Integer.toHexString(0xff & h);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
