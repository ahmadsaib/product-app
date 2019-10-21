package com.product.app.service;

import com.product.app.dto.LoginUser;
import com.product.app.model.User;

public interface UserService {
  void addUser(User user);

  User loginUser(LoginUser loginUser);
}
