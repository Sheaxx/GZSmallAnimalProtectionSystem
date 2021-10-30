package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Notice;
import com.gzsaps.java.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/notice")
public class NoticeHandler {

  @Autowired
  private NoticeRepository noticeRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<Notice> findAll(@PathVariable("page") Integer page,@PathVariable("size") Integer size) {
    Pageable pageable = PageRequest.of(page-1,size);
    return noticeRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public Notice find(@PathVariable("id") Integer id) {
    Optional<Notice> result = noticeRepository.findById(id);
    if(result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  @PostMapping("/save")
  public String save(@RequestBody Notice notice) {
    notice.setCreatetime(new Date());
    notice.setLastmodifiedtime(new Date());
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
