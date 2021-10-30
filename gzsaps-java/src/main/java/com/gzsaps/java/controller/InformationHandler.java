package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Information;
import com.gzsaps.java.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/information")
public class InformationHandler {
  @Autowired
  private InformationRepository informationRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<Information> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
    Pageable pageable = PageRequest.of(page-1,size);
    return informationRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public Information find(@PathVariable("id") Integer id) {
    Optional<Information> result = informationRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  @PostMapping("/save")
  public String save(@RequestBody Information information) {
    Information result = informationRepository.save(information);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Information information) {
    Information result = informationRepository.save(information);
    if (result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    informationRepository.deleteById(id);
  }
}
