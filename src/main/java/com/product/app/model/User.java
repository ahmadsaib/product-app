package com.product.app.model;

import lombok.*;

@Getter
@Setter
public class User {
  private Integer id;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
}
