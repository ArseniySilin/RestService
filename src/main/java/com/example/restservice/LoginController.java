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
    Map<Integer, String> messagesByCode = new HashMap<>();

    public LoginController() {
        messagesByCode.put(0, "Success");
        messagesByCode.put(1, "Technical error");
        messagesByCode.put(2, "Username do not exist");
        messagesByCode.put(3, "Incorrect password");
    }

    private boolean isUserExist(String username) {
        String validUsername = "test";

        return username.equals(validUsername);
    }

    private boolean isPasswordCorrect(String password) {
        String validPassword = "test";

        return password.equals(validPassword);
    }

    private int login(User user) {
//        try {
//            Connection conn = null;
////            String db = "jdbc:hsqldb:hsql://localhost/sampledb;ifexists=true";
//
//            // TODO: remove hardcode
//            String username = "admin";
//            String password = "admin";
//            conn = DriverManager.getConnection("jdbc:", username, password);
//
//            if (!isUserExist(user.getUsername())) {
//                return 3;
//            }
//
//            if (!isPasswordCorrect(user.getUsername())) {
//                return 4;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            return 2;
//        }

        return 0;
    }

    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response response(@RequestBody User user) {
        int resultCode = login(user);

        return new Response(resultCode, messagesByCode.get(resultCode));
    }
}