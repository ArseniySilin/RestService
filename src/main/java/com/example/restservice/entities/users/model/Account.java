package com.example.restservice.entities.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Account {
  @Getter
  private String username;
  @Getter
  private String password;
}

