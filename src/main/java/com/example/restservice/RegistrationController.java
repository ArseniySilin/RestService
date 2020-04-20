package com.example.restservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {
    Map<Integer, String> messagesByCode = new HashMap<>();

    public RegistrationController() {
        messagesByCode.put(0, "Success");
        messagesByCode.put(1, "Login already exists");
        messagesByCode.put(2, "Technical error");
    }

    private boolean isExist(String username) {
        String existingUsername = "test";

        return username.equals(existingUsername);
    }

    private int addUser(User user) {
        try {
            // add to db
            if (isExist(user.getUsername())) return 1;
        } catch (Exception e) {
            return 2;
        }

        return 0;
    }

    @PostMapping(
            path = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response response(@RequestBody User user) {

        int resultCode = addUser(user);

        return new Response(resultCode, messagesByCode.get(resultCode));
    }
}
