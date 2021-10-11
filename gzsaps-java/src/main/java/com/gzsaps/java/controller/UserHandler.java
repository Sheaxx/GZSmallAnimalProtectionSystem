package com.gzsaps.java.controller;

import com.gzsaps.java.entity.User;
import com.gzsaps.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserHandler {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/findAll")
  public List<User> findAll() {
    return userRepository.findAll();
  }
}
