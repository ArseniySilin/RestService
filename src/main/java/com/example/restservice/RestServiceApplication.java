package com.example.restservice;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.crypto.SecretKey;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {


		TestDB db = new TestDB();

		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String secretString = Encoders.BASE64.encode(key.getEncoded());

		System.out.println("SecretKey: " + secretString);

		SpringApplication.run(RestServiceApplication.class, args);
	}

}


