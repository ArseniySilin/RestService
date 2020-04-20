package com.example.restservice;

public class User {
    private final int id;
    private final String username;
    private final String password;

    User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }
}
