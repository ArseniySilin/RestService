package com.example.restservice;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        // TODO: fix hardcode
        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/test");
        ds.setUsername("admin");
        ds.setPassword("admin");
        ds.setMinIdle(5);
        ds.setMaxIdle(100);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() {}
}