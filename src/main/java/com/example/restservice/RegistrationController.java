package com.example.restservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {
    public static final String path = "/register";

    public RegistrationController() {}

    private boolean isExist(String username) {
        String existingUsername = "test";

        return username.equals(existingUsername);
    }

    private int addUser(User user) {
        try {
            // add to db

        } catch (Exception e) {
            return 2;
        }

        return 0;
    }

    boolean getPasswordSafety(String password) {
        // TODO: add more checks
        return hasMinimumSafetyLength(password);
    }

    boolean hasMinimumSafetyLength(String str) {
        return str != null && str.length() >= 6;
    }

    @PostMapping(
        path = "/register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response response(@RequestBody User user) {
        String username = user.getUsername();

        if (!hasMinimumSafetyLength(username)) {
            return new Response(Messages.ERROR.UNSAFE_USERNAME.code, Messages.ERROR.UNSAFE_USERNAME.message);
        }
        int userExistCode = DB.isUserExist(username);

        if (userExistCode != Messages.SUCCESS.code) {
            return new Response(userExistCode, Messages.getMessageByCode(userExistCode));
        }

        boolean isPasswordSafe = getPasswordSafety(user.getPassword());
        if (!isPasswordSafe) {
            return new Response(Messages.ERROR.UNSAFE_PASSWORD.code, Messages.ERROR.UNSAFE_PASSWORD.message);
        }

        int resultCode = addUser(user);
        return new Response(resultCode, Messages.getMessageByCode(resultCode));
    }
}
