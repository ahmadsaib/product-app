package com.product.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.product.app.dto.LoginUser;
import com.product.app.model.User;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired private BCryptPasswordEncoder bcryptEncoder;

  @Override
  public void addUser(User user) {
    String encriptedPassword = bcryptEncoder.encode(user.getPassword());
    user.setPassword(encriptedPassword);
  }

  private List<SimpleGrantedAuthority> getAuthority() {
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  public User loginUser(LoginUser loginUser) {
    User user = new User();
    user.setUsername(loginUser.getUsername());
    user.setPassword(loginUser.getPassword());
    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new org.springframework.security.core.userdetails.User("admin", "admin", getAuthority());
  }
}
