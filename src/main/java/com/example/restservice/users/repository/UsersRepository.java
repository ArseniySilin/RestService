package com.example.restservice.users.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.restservice.*;
import com.example.restservice.users.exceptions.UsersException;
import com.example.restservice.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// TODO: move class to ./repository

@Repository
public class UsersRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public int updateUserTokens(String username, String accessToken, String refreshToken) throws UsersException {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt =
              con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE login = ?");
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
                    throw new UsersException(Messages.ERROR.INCORRECT_PASSWORD.message);
                }

                return Messages.SUCCESS.code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(Messages.ERROR.message);
        }

        throw new UsersException(Messages.ERROR.USERNAME_DO_NOT_EXIST.message);
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

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String id = rs.getString("id");
                String password = rs.getString("password");

                user = new User(id, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsersException(e.getMessage());
        }

        return user;
    }

    public int validateUserTokens(String username, String accessToken, String refreshToken) throws UsersException {
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

    public void addUser(com.example.restservice.users.model.User user) throws UsersException {
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

            throw new UsersException(e);
        }
    }
}
