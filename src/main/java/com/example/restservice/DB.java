package com.example.restservice;

import java.sql.*;

public class DB {
    public static int isUserExist(String login, String password) {
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String userPassword = rs.getString("password");

                if (password.equals(userPassword)) {
                    return Messages.SUCCESS.code;
                }

                return Messages.ERROR.INCORRECT_PASSWORD.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Messages.ERROR.code;
        }

        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
    }

    public void test() {
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");

            while (rs.next()) {
                int id = rs.getInt("id");
                String login = rs.getString("login");
                System.out.println("id: " + id + " login: " + login);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
