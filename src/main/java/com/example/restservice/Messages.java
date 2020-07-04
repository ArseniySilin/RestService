package com.example.restservice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Messages {
    private static int MINIMUM_FIELDS_NUMBER = 2;

    public static Map<Integer, String> loginControllerMessages = new HashMap<>();

    public static class SUCCESS {
        public static final int code = 0;
        public static final String message = "SUCCESS";
    }

    public static class ERROR {
        public static final int code = 1;
        public static final String message = "FAILURE";

        public static class USERNAME_DO_NOT_EXIST {
            public static final int code = 2;
            public static final String message = "Username do not exist";
        }
        public static class INCORRECT_PASSWORD {
            public static final int code = 3;
            public static final String message = "Incorrect password";
        }

        public static class INVALID_TOKEN {
            public static final int code = 4;
            public static final String message = "Invalid token";
        }

        public static class USER_ALREADY_EXISTS {
            public static final int code = 5;
            public static final String message = "User already exists";
        }

        public static class UNSAFE_PASSWORD {
            public static final int code = 6;
            public static final String message = "Password is not safe enough";
        }

        public static class UNSAFE_USERNAME {
            public static final int code = 7;
            public static final String message = "Username is not safe enough";
        }

        public static class DATABASE_ERROR {
            public static final int code = 8;
            public static final String message = "Database error";
        }
    }

    private static final Messages m = new Messages();


    private Messages() {
        // add all necessary messages
        loginControllerMessages.put(SUCCESS.code, SUCCESS.message);
        loginControllerMessages.put(ERROR.code, ERROR.message);
        loginControllerMessages.put(ERROR.USERNAME_DO_NOT_EXIST.code, ERROR.USERNAME_DO_NOT_EXIST.message);
        loginControllerMessages.put(ERROR.INCORRECT_PASSWORD.code, ERROR.INCORRECT_PASSWORD.message);
    }

    public static Messages getInstance() {
        return m;
    }

    static private String getMessageByCode(Class c, int code) {
        Field[] allFields = c.getDeclaredFields();
        Class[] allSubClasses = c.getDeclaredClasses();

        if (allFields.length < MINIMUM_FIELDS_NUMBER) {
            return null;
        }

        Field codeField = allFields[0];
        Field messageField = allFields[1];

        boolean doesFieldsHaveValidNames = codeField.getName().equals("code") &&
                messageField.getName().equals("message");

        if (!doesFieldsHaveValidNames) return null;

        try {
            Object value = codeField.get(getInstance()); // TODO: is it ok to pass this instance?
            if (value.equals(code)) {
                Object messageValue = messageField.get(getInstance());

                return messageValue.toString();
            }
        } catch (IllegalAccessException e) {
            System.out.println(e);
        }

        if (allSubClasses.length > 0) {
            for (Class allSubClass : allSubClasses) {
                String subClassMessage = getMessageByCode(allSubClass, code);

                if (subClassMessage != null) return subClassMessage;
            }
        }

        return null;
    }

    public static String getMessageByCode(int code) {
        Class[] allClasses = Messages.class.getDeclaredClasses();

        for (Class allClass : allClasses) {
            String message = getMessageByCode(allClass, code);

            if (message != null) {
                return message;
            }
        }

        return null;
    }
}
