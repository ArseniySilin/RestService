package com.example.restservice;

import java.util.UUID;

public class TestDB {
    public TestDB() {

        test();
    }

    public void test()  {
        DB db = new DB();
//        int foo = db.loginUser("first", "first");

//        String message = Messages.getMessageByCode(foo);

//        System.out.println("message: " + message);


        String uniqueID = UUID.randomUUID().toString();
        System.out.println("UUID: " + uniqueID);
//
//        Token t = new Token();
//        t.test();

//        String password = "first";
//        System.out.println("Test hashing...");
//        HashString h = new HashString();
//        String hashedPassword = h.getHash(password);
//
//        System.out.println("hashedPassword: " + hashedPassword);
//        System.out.println("isMatches: " + h.isMatches(hashedPassword, "passwor"));
    }
}
