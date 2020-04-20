package com.example.restservice;
import java.net.URI;
import java.sql.*;
import org.apache.commons.dbcp2.*;

// TODO: Refactor: make a singleton
public class ConnectionPool {
    private BasicDataSource connectionPool;

    public ConnectionPool() {
        initialize();
    }

    private void initialize()  {
        String dbUrl = "jdbc:postgresql://127.0.0.1:5432/test";
        connectionPool = new BasicDataSource();

        connectionPool.setUsername("admin");
        connectionPool.setPassword("admin");

        connectionPool.setDriverClassName("org.postgresql.Driver");
        connectionPool.setUrl(dbUrl);
        connectionPool.setInitialSize(5);
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}
