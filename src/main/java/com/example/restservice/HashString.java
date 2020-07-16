package com.example.restservice;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class HashString {
    Pbkdf2PasswordEncoder encoder;

    public HashString() {
        // TODO: read secret from app properties
        encoder = new Pbkdf2PasswordEncoder("secret", 10000, 128);
    }

    public String getHash(String password)  {
        return encoder.encode(password);
    }

    public boolean isMatches(String hash, String password) {
        return encoder.matches(password, hash);
    }
}
