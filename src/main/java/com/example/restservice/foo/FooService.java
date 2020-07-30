package com.example.restservice.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FooService {

  @Autowired
  private FooRepository fooRepository;

  public List<Foo> findAll() {

    fooRepository.findAll();


    return null;
  }

  public Long count() {

    return fooRepository.count();
  }

//  public void deleteByKey(String key) {
//
//    fooRepository.deleteById(key);
//  }
}
