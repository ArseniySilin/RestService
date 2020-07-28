package com.example.restservice;

import com.example.restservice.foo.Foo;
import com.example.restservice.foo.FooDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);

//		System.out.println(">>>>>>>>>>>>>>>>>>>>");
//		FooDao fooDao = new FooDao();
//
//		fooDao.saveStudent(new Foo("10d0-1111-222-3333", "goga332"));
//
//		List<Foo> foos = fooDao.getFoos();
//		foos.forEach(s -> System.out.println(s.getUsername()));
//		System.out.println("<<<<<<<<<<<<<<<<<<<<");
	}

}


