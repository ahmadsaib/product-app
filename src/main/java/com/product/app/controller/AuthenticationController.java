package com.product.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.product.app.config.JwtTokenUtil;
import com.product.app.constants.AuthorizationConstants;
import com.product.app.dto.ApiResponseDto;
import com.product.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.product.app.dto.LoginUser;

@CrossOrigin(origins = "*", maxAge = 360000000)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private static final String TOKEN = "token";

  private static final String USER = "user";

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Autowired private UserDetailsService userDetailsService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ApiResponseDto login(@RequestBody LoginUser loginUser) throws AuthenticationException {
    ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

    if (loginUser.getUsername().equals("admin") && loginUser.getPassword().equals("admin")) {
      final UserDetails user = userDetailsService.loadUserByUsername(loginUser.getUsername());
      final String token = jwtTokenUtil.generateToken(user);
      Map<String, Object> response = setTokenDetails(user, token);
      apiResponseDtoBuilder
          .withStatus(HttpStatus.OK)
          .withMessage(AuthorizationConstants.LOGIN_SUCESSFULL)
          .withData(response);
    } else {
      apiResponseDtoBuilder.withStatus(HttpStatus.UNAUTHORIZED).withMessage("Invalid Credentials");
    }

    return apiResponseDtoBuilder.build();
  }

  private Map<String, Object> setTokenDetails(final UserDetails user, final String token) {
    Map<String, Object> response = new HashMap<>();
    response.put(USER, user);
    response.put(TOKEN, token);
    return response;
  }
}
