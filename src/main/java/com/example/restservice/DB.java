package com.example.restservice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;

public class DB {
    public static boolean updateUserTokens(String username, String accessToken, String refreshToken) {
        boolean didUpdateSuccessfully = false;

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt =
              con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE login = ?");
            pstmt.setString(1, accessToken);
            pstmt.setString(2, refreshToken);
            pstmt.setString(3, username); // TODO: rename login to username in DB

            didUpdateSuccessfully = pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return didUpdateSuccessfully;
    }

    public static int isUserExist(String login, String password) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String userPasswordHash = rs.getString("password");
                HashString hasher = new HashString();
                boolean isPasswordMatches = hasher.isMatches(userPasswordHash, password);

                if (isPasswordMatches) {
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

    public static int isUserExist(String login) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                return Messages.SUCCESS.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Messages.ERROR.code;
        }

        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
    }

    public static int isUserExist(String username, String accessToken, String refreshToken) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String userAccessToken = rs.getString("access_token");
                String userRefreshToken = rs.getString("refresh_token");
                boolean areTokensMatches = userAccessToken.equals(accessToken) && userRefreshToken.equals(refreshToken);

                if (areTokensMatches) {
                    return Messages.SUCCESS.code;
                }

                // TODO: add custom messages for token dismatching error
                return Messages.ERROR.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Messages.ERROR.code;
        }

        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
    }

    public static User getUserByUserName(String username) {
        User user = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                String userPassword = rs.getString("password");
                List authorities = new ArrayList<>();
                user = new User(username, userPassword, authorities);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static int addUser(com.example.restservice.User user) { // TODO: unify users classes
        PreparedStatement pstmt;

        try {
            Connection con = DBCPDataSource.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) return Messages.ERROR.USER_ALREADY_EXISTS.code;

            pstmt = con.prepareStatement("INSERT INTO users (login, password, created_on) VALUES (?, ?, ?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, new HashString().getHash(user.getPassword()));
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            return Messages.ERROR.code;
        }

        return Messages.SUCCESS.code;
    }
}
