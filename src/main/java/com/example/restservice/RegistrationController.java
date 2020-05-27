package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

@RestController
public class RegistrationController {
    @Autowired
    private UsersRepository usersRepository;

    public static final String path = "/register";

    public RegistrationController() {}

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

        boolean isPasswordSafe = getPasswordSafety(user.getPassword());
        if (!isPasswordSafe) {
            return new Response(Messages.ERROR.UNSAFE_PASSWORD.code, Messages.ERROR.UNSAFE_PASSWORD.message);
        }

        int resultCode = usersRepository.addUser(user);
        return new Response(resultCode, Messages.getMessageByCode(resultCode));
    }
}
