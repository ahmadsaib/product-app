package com.product.app.dto;

import lombok.*;

@Getter
@Setter
public class ApiResponse {

  private Integer status;
  private String message;
  private Object result;

  public ApiResponse(int status, String message, Object result) {
    this.status = status;
    this.message = message;
    this.result = result;
  }
}
