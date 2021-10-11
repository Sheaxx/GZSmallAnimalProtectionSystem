package com.gzsaps.java.controller;

import com.gzsaps.java.entity.AdoptionImage;
import com.gzsaps.java.repository.AdoptionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoptionImage")
public class AdoptionImageHandler {

  @Autowired
  private AdoptionImageRepository adoptionImageRepository;

  @GetMapping("/findAll")
  public List<AdoptionImage> findAll() {
    return adoptionImageRepository.findAll();
  }

  @PostMapping("/save")
  public String save(@RequestBody AdoptionImage image) {
    AdoptionImage result = adoptionImageRepository.save(image);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody AdoptionImage image) {
    AdoptionImage result = adoptionImageRepository.save(image);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    adoptionImageRepository.deleteById(id);
  }
}
