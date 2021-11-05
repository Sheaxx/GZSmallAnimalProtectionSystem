package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Comment;
import com.gzsaps.java.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentHandler {
  @Autowired
  private CommentRepository commentRepository;

  @GetMapping("/findAll/{id}")
  public List<Comment> findAll(@PathVariable("id") Integer id) {//查看某条领养信息下的所有评论
    List<Comment> list = commentRepository.findAll();
    List<Comment> result = new ArrayList<>();
    for(int i=0;i<list.size();i++) {
      if (list.get(i).getAdoptionid() == id) {
        result.add(list.get(i));
      }
    }
    return result;
  }

  @PostMapping("/save")
  public String save(@RequestBody Comment comment) {//添加评论
    comment.setCreatetime(new Date());
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
