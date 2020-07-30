package com.example.restservice.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FooController {
  @Autowired
  FooRepository fooRepository;

  @GetMapping(value = "foo")
  public void getBar() {
    long lolo = fooRepository.count();
    System.out.println("fooRepository.count(): " + lolo);

    List<Foo> f = fooRepository.findAll();
    f.forEach(i -> System.out.println(i.getUsername()));
  }
}
