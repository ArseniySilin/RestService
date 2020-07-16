package com.example.restservice.execptions;

public class EntityAlreadyExistsException extends RuntimeException {
  public EntityAlreadyExistsException(Class clazz, String... searchParamsMap) {
    super(clazz.getSimpleName() + " already exists");
  }
}
