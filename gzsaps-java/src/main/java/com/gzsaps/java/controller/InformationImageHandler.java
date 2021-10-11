package com.gzsaps.java.controller;

import com.gzsaps.java.entity.InformationImage;
import com.gzsaps.java.repository.InformationImageRepository;
import com.gzsaps.java.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/informationImage")
public class InformationImageHandler {
  @Autowired
  private InformationImageRepository informationImageRepository;

  @GetMapping("/findAll")
  public List<InformationImage> findAll() {
    return informationImageRepository.findAll();
  }

  @PostMapping("/save")
  public String save(@RequestBody InformationImage image) {
    InformationImage result = informationImageRepository.save(image);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody InformationImage image) {
    InformationImage result = informationImageRepository.save(image);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    informationImageRepository.deleteById(id);
  }
}
