package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
//import TestDB;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
//		DBConnectionPool dpConnectionPool = new DBConnectionPool();
//		Connection connection = dpConnectionPool.getConnection();
//		Connection connection = DBConnectionPool.create();


//		 dpConnectionPool.getConnection();

		TestDB db = new TestDB();

//		db.test();
		SpringApplication.run(RestServiceApplication.class, args);
	}

}


