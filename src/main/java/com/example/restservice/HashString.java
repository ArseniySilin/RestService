package com.example.restservice;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class HashString {
    Pbkdf2PasswordEncoder encoder;

    HashString() {
        // TODO: read secret from app properties
        encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
    }

    String getHash(String password)  {
        return encoder.encode(password);
    }

    boolean isMatches(String hash, String password) {
        return encoder.matches(password, hash);
    }
}
