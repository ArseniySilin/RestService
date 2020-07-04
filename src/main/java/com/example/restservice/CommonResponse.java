package com.example.restservice;

import com.example.restservice.apierror.ApiSubError;

import java.util.List;

public class CommonResponse {
  private String code;
  private String resultCode;
  private List<ApiSubError> errors;
  private Object data;


  public CommonResponse(String code, String resultCode, List<ApiSubError> errors, Object data) {
    this.code = code;
    this.resultCode = resultCode;
    this.errors = errors;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public String getResultCode() {
    return resultCode;
  }

  public List<ApiSubError> getErrors() {
    return errors;
  }

  public Object getData() {
    return data;
  }
}
