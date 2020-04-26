package com.example.restservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    public LoginController() {}

    private boolean isUserExist(String username) {
        String validUsername = "test";

        return username.equals(validUsername);
    }

    private boolean isPasswordCorrect(String password) {
        String validPassword = "test";

        return password.equals(validPassword);
    }

    private int login(User user) {
        return DB.isUserExist(user.getUsername(), user.getPassword());
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response response(@RequestBody User user) {
        int resultCode = login(user);
        String message = Messages.getMessageByCode(resultCode);

        return new Response(resultCode, message);
    }
}