package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Information;
import com.gzsaps.java.entity.Notice;
import com.gzsaps.java.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationHandler {
  @Autowired
  private InformationRepository informationRepository;

  @GetMapping("/findAll")
  public List<Information> findAll() {
    return informationRepository.findAll();
  }

  @GetMapping("/find/{id}")
  public Information find(@PathVariable("id") Integer id) {
    return informationRepository.findById(id).get();
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
