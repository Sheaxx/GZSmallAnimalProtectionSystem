package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Comment;
import com.gzsaps.java.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentHandler {
  @Autowired
  private CommentRepository commentRepository;

  @GetMapping("/findAll")
  public List<Comment> findAll() {
    return commentRepository.findAll();
  }

  @PostMapping("/save")
  public String save(@RequestBody Comment comment) {
    Comment result = commentRepository.save(comment);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    commentRepository.deleteById(id);
  }
}
