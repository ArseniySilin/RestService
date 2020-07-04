package com.example.restservice.apierror;

import org.springframework.http.HttpStatus;
import java.util.List;


public class ApiError {

  private HttpStatus status;
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//  private LocalDateTime timestamp;
  private String message;
  private int code;
  private List<ApiSubError> errors;

  private ApiError() {
//    timestamp = LocalDateTime.now();
  }

  public ApiError(HttpStatus status) {
    this();
    this.status = status;
  }

  public ApiError(HttpStatus status, Throwable ex) {
    this();
    this.status = status;
    this.message = "Unexpected error";
  }

  public ApiError(HttpStatus status, String message, Throwable ex) {
    this();
    this.status = status;
    this.message = message;
  }

  public ApiError(HttpStatus status, int code) {
    this();
    this.status = status;
    this.code = code;
  }

  public ApiError(HttpStatus status, int code, String message) {
    this();
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public int getCode() { return this.code; }

  public String getMessage() { return this.message; }
}
