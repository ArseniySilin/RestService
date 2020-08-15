package com.example.restservice.entities.users.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.restservice.*;
import com.example.restservice.entities.users.exceptions.UsersException;
import com.example.restservice.entities.users.model.User;
import com.example.restservice.entities.users.model.UserTokens;
import com.example.restservice.utils.HashString;
import com.example.restservice.utils.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public int updateUserTokens(String username, String accessToken, String refreshToken) throws UsersException {
        try (
          Connection con = DBCPDataSource.getConnection();
          PreparedStatement pstmt = con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE login = ?")
          ) {
            pstmt.setString(1, accessToken);
            pstmt.setString(2, refreshToken);
            pstmt.setString(3, username);

            if (pstmt.executeUpdate() != 1) throw new UsersException(Messages.ERROR.message);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e.getMessage());
        }

        return Messages.SUCCESS.code;
    }

    public int authorizeUser(String login, String password) throws UsersException {
        String sqlQuery = "SELECT * FROM users WHERE login = ?";
        boolean isUserExist = false;

        try (
          Connection con = DBCPDataSource.getConnection();
          PreparedStatement pstmt = con.prepareStatement(sqlQuery);
          ) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isLoginExist = rs.next();

            if (isLoginExist) {
                String userPasswordHash = rs.getString("password");
                HashString hasher = new HashString();
                boolean isPasswordMatches = hasher.isMatches(userPasswordHash, password);

                if (isPasswordMatches) {
                    isUserExist = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e.getMessage());
        }
        if (isUserExist) return Messages.SUCCESS.code;

        return Messages.ERROR.INCORRECT_PASSWORD.code;
    }

    public UserTokens generateUserTokens(String username) {
        User user = getUser(username);
        String accessToken = jwtTokenUtil.generateAccessToken(username, user.getId());
        String refreshToken  = jwtTokenUtil.generateRefreshToken();
        UserTokens userToken = new UserTokens(accessToken, refreshToken);

        return userToken;
    }

    public User getUser(String username) throws UsersException {
        User user = null;
        String sqlQuery = "SELECT * FROM users WHERE login = ?";

        try (
          Connection con = DBCPDataSource.getConnection();
          PreparedStatement pstmt = con.prepareStatement(sqlQuery)
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String key = rs.getString("key");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                user = new User(id, key, username, password, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e.getMessage());
        }

        return user;
    }

    public int validateUserTokens(String username, String accessToken, String refreshToken) throws UsersException {
        try (
          Connection con = DBCPDataSource.getConnection();
          PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?")
          ) {
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

                throw new UsersException(Messages.ERROR.INVALID_TOKEN.message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e.getMessage());
        }
        throw new UsersException(Messages.ERROR.USERNAME_DO_NOT_EXIST.message);
    }

    public org.springframework.security.core.userdetails.User getUserByUserName(String username) throws UsersException {
        User user = getUser(username);
        List authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
          username,
          user.getPassword(),
          authorities
        );
    }

    public void addUser(com.example.restservice.entities.users.model.User user) throws UsersException {
        String userName = user.getUsername();
        String password = user.getPassword();
        String query = "INSERT INTO users (login, password, created_on, key) VALUES (?, ?, ?, ?)";

        try (
          Connection con = DBCPDataSource.getConnection();
          PreparedStatement pstmt = con.prepareStatement(query)
          ) {
            pstmt.setString(1, userName);
            pstmt.setString(2, new HashString().getHash(password));
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4, UUID.randomUUID().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            throw new UsersException(e);
        }
    }

    public String getUserKeyById(Connection con, String userId) throws UsersException {
        String userKey = null;

        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(userId));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                userKey = rs.getString("key");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e);
        }

        return userKey;
    }
}
