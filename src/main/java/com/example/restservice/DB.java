package com.example.restservice;

import java.sql.*;

//public class DB {
//    private static boolean updateUserTokens(Connection con, int userId, String login)
//        throws SQLException {
//            PreparedStatement pstmt =
//                con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE id = ?");
//            pstmt.setString(1, Tokenize.generateAccessToken(login));
//            pstmt.setString(2, Tokenize.generateRefreshToken());
//            pstmt.setInt(3, userId);
//
//            boolean didUpdateSuccessfully = pstmt.executeUpdate() == 1;
//
//            return didUpdateSuccessfully;
//    }
//
//    public static int loginUser(String login, String password) {
//        try {
//            Connection con = DBCPDataSource.getConnection();
//            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
//            pstmt.setString(1, login);
//            ResultSet rs = pstmt.executeQuery();
//            boolean isExist = rs.next();
//
//            if (isExist) {
//                String userPasswordHash = rs.getString("password");
////                int userId = rs.getInt("id");
//                HashString hasher = new HashString();
//                boolean isPasswordMatches = hasher.isMatches(userPasswordHash, password);
//
//                if (isPasswordMatches) {
//                    // update user tokens in DB
////                    updateUserTokens(con, userId, login);
//
//                    return Messages.SUCCESS.code;
//                }
//
//                return Messages.ERROR.INCORRECT_PASSWORD.code;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return Messages.ERROR.code;
//        }
//
//        return Messages.ERROR.USERNAME_DO_NOT_EXIST.code;
//    }
//
////    public void test() {
////        try {
////            Connection con = DBCPDataSource.getConnection();
////            Statement stmt = con.createStatement();
////            ResultSet rs = stmt.executeQuery("SELECT * FROM users;");
////
////            while (rs.next()) {
////                int id = rs.getInt("id");
////                String login = rs.getString("login");
////                System.out.println("id: " + id + " login: " + login);
////
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//}
public class DB {
    public static boolean updateUserTokens(User user, String accessToken, String refreshToken) {
        boolean didUpdateSuccessfully = false;

        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt =
              con.prepareStatement("UPDATE users SET access_token = ?, refresh_token = ? WHERE id = ?");
            pstmt.setString(1, accessToken);
            pstmt.setString(2, refreshToken);
            pstmt.setInt(3, user.getId());

            didUpdateSuccessfully = pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return didUpdateSuccessfully;
    }

    public static int loginUser(String login, String password) {
        try {
            Connection con = DBCPDataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE login = ?");
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            boolean isExist = rs.next();

            if (isExist) {
                String userPasswordHash = rs.getString("password");
//                int userId = rs.getInt("id");
                HashString hasher = new HashString();
                boolean isPasswordMatches = hasher.isMatches(userPasswordHash, password);

                if (isPasswordMatches) {
                    // update user tokens in DB
//                    updateUserTokens(con, userId, login);

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
}
