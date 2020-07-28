package com.example.restservice.foo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "foo")
public class Foo implements Serializable {

  @Column(name = "username")
  private String username;

  @Id
  @Column(name = "key")
  private String key;

  public Foo() {}

  public Foo(String username, String key) {
    this.username = username;
    this.key = key;
  }


  public String getUsername() {
    return username;
  }

  public String getKey() {
    return key;
  }
}
