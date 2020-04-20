package com.example.restservice;

import java.sql.*;


public class TestDB {

    public TestDB() {
        System.out.println("TestDB constructor");


        test();
    }
//    public void test(Connection dbConnection) {
//        System.out.println(">>>>>>>>>>> TEST <<<<<<<<<<<");
//
//        Map<String, String> klop = new LinkedHashMap<>();
////        klop.entrySet().iterator();
//
//        try {
//            Connection conn = null;
//            Statement stmt = null;
////            String db = "jdbc:hsqldb:hsql://localhost/sampledb;ifexists=true";
//
//            // TODO: remove hardcode
//            String username = "admin";
//            String password = "admin";
//            conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", username, password);
//            System.out.println("Connected to db successfully");
//
//            stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                System.out.println(id);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
    public void test()  {
        DB db = new DB();
        db.isUserExist("first", "first");
    }
}
