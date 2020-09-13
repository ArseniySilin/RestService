package com.example.restservice.entities.common;

import com.example.restservice.apierror.ApiSubError;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CommonResponse {
  private String code;
  private int resultCode;
  private List<ApiSubError> errors;
  private Object data;
}
