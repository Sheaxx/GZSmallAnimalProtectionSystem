package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Notice;
import com.gzsaps.java.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeHandler {

  @Autowired
  private NoticeRepository noticeRepository;

  @GetMapping("/findAll")
  public List<Notice> findAll() {
    return noticeRepository.findAll();
  }

  @GetMapping("/find/{id}")
  public Notice find(@PathVariable("id") Integer id) {
    return noticeRepository.findById(id).get();
  }

  @PostMapping("/save")
  public String save(@RequestBody Notice notice) {
    Notice result = noticeRepository.save(notice);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Notice notice){
    Notice result = noticeRepository.save(notice);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    noticeRepository.deleteById(id);
  }
}
