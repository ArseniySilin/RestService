package com.example.restservice;

import java.util.HashMap;
import java.util.Map;

public class Messages {
    public static Map<Integer, String> loginControllerMessages = new HashMap<>();
    public static class SUCCESS {
        static final int code = 0;
        static final String message = "Success";
    }
    public static class ERROR {
        static final int code = 1;
        static final String message = "Technical error";

        public static class USERNAME_DO_NOT_EXIST {
            static final int code = 2;
            static final String message = "Username do not exist";
        }
        public static class INCORRECT_PASSWORD {
            static final int code = 3;
            static final String message = "Incorrect password";
        }
    }

    private static final Messages m = new Messages();


    private Messages() {
        System.out.println("Messages constructor");

        loginControllerMessages.put(SUCCESS.code, SUCCESS.message);
        loginControllerMessages.put(ERROR.code, ERROR.message);
        loginControllerMessages.put(ERROR.USERNAME_DO_NOT_EXIST.code, ERROR.USERNAME_DO_NOT_EXIST.message);
        loginControllerMessages.put(ERROR.INCORRECT_PASSWORD.code, ERROR.INCORRECT_PASSWORD.message);
    }

    public static Messages getInstance() {
        return m;
    }
}
