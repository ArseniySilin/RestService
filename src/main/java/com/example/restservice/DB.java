package com.example.restservice;

import java.sql.*;

public class DB {
    public boolean isUserExist(String login, String password) {
        try {
            Connection con = DBCPDataSource.getConnection();
            Statement stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("id: " + id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
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
