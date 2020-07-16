package com.example.restservice.accounts.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.restservice.*;
import com.example.restservice.accounts.exceptions.AccountsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

// TODO: move class to ./repository

@Repository
public class UsersRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public int updateUserTokens(String username, String accessToken, String refreshToken) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt =
              con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE login = ?");
            pstmt.setString(1, accessToken);
            pstmt.setString(2, refreshToken);
            pstmt.setString(3, username); // TODO: rename login to username in UsersRepository

            if (pstmt.executeUpdate() != 1) return Messages.ERROR.code;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Messages.SUCCESS.code;
    }

    public int authorizeUser(String login, String password) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isLoginExist = rs.next();

            if (isLoginExist) {
                String userPasswordHash = rs.getString("password");
                HashString hasher = new HashString();
                boolean isPasswordMatches = hasher.isMatches(userPasswordHash, password);

                if (!isPasswordMatches) {
                    return Messages.ERROR.INCORRECT_PASSWORD.code;
                }


                return Messages.SUCCESS.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Messages.ERROR.code;
        }

        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
    }

    public UserTokens generateUserTokens(String username) {
        com.example.restservice.accounts.model.User user = getUser(username);
        String accessToken = jwtTokenUtil.generateAccessToken(username, user.getId());
        String refreshToken  = jwtTokenUtil.generateRefreshToken();
        UserTokens userToken = new UserTokens(accessToken, refreshToken);

        return userToken;
    }

    public com.example.restservice.accounts.model.User getUser(String username) {
        com.example.restservice.accounts.model.User user = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String id = rs.getString("id");
                String password = rs.getString("password");

                user = new com.example.restservice.accounts.model.User(id, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // throw not found exception
        }

        return user;
    }

    public int validateUserTokens(String username, String accessToken, String refreshToken) {
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

                return Messages.ERROR.INVALID_TOKEN.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Messages.ERROR.DATABASE_ERROR.code;
        }

        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
    }

    public User getUserByUserName(String username) {
        User user = null;

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String userPassword = rs.getString("password");
                List authorities = new ArrayList<>();
                user = new User(username, userPassword, authorities);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void addUser(com.example.restservice.accounts.model.User user) throws AccountsException {
        String userName = user.getUsername();
        String password = user.getPassword();

        try {
            Connection con = DBCPDataSource.getConnection();

            String query = "INSERT INTO users (login, password, created_on, key) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userName);
            pstmt.setString(2, new HashString().getHash(password));
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4, UUID.randomUUID().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            throw new AccountsException(e);
        }
    }
}
