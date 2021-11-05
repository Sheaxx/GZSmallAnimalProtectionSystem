package com.gzsaps.java.controller;

import com.gzsaps.java.entity.User;
import com.gzsaps.java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/find/{username}")
  public User find(@PathVariable("username") String username) {
    return userRepository.findById(username).get();
  }

  @GetMapping("/signUp/{username}/{password}")
  public User signUp (@PathVariable("username") String username,@PathVariable("password") String password) {
    User example = userRepository.findById(username).get();
    if (password.equals(example.getPassword())) {
      return example;
    } else {
      return null;
    }
  }

  @PostMapping("/signIn")
  public String save(@RequestBody User user) {
    User result = userRepository.save(user);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody User user) {
    User result = userRepository.save(user);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }
}
