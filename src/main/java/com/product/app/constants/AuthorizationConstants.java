package com.product.app.constants;

public class AuthorizationConstants {
  private AuthorizationConstants() {
    throw new IllegalStateException("Authorization Constants");
  }

  public static final long ACCESS_TOKEN_VALIDITY_SECONDS = (long) 5 * 60 * 60;
  public static final String SIGNING_KEY = "snap-app";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "authorization";
  public static final String LOGIN_SUCESSFULL = "Login Sucessfull";
}
