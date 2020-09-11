package com.example.restservice.entities.common;

import com.example.restservice.apierror.ApiSubError;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class CommonResponse {
  @Getter
  private String code;
  @Getter
  private int resultCode;
  @Getter
  private List<ApiSubError> errors;
  @Getter
  private Object data;
}
