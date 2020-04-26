package com.example.restservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
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
        return DB.loginUser(user.getUsername(), user.getPassword());
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response response(@RequestBody User user) {
        int resultCode = DB.loginUser(user.getUsername(), user.getPassword());
        String message = Messages.getMessageByCode(resultCode);

        if (resultCode != Messages.SUCCESS.code) {
            return new Response(resultCode, message);
        }

        Token token = Tokenize.generateToken(user);
        Gson gson = new Gson();
        String data = gson.toJson(token);

        return new Response(resultCode, message, data);
    }
}