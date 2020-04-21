package com.example.restservice;

public class TestDB {
    public TestDB() {

        test();
    }

    public void test()  {
        DB db = new DB();
        int foo = db.isUserExist("first", "first");

        String message = Messages.getMessageByCode(foo);

        System.out.println("message: " + message);
    }
}
