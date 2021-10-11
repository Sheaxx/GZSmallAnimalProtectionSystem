package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Adoption;
import com.gzsaps.java.entity.Notice;
import com.gzsaps.java.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adoption")
public class AdoptionHandler {
  @Autowired
  private AdoptionRepository adoptionRepository;

  @GetMapping("/findAll")
  public List<Adoption> findAll() {
    return adoptionRepository.findAll();
  }

  @GetMapping("/find/{id}")
  public Adoption find(@PathVariable("id") Integer id) {
    return adoptionRepository.findById(id).get();
  }


  @PostMapping("/save")
  public String save(@RequestBody Adoption adoption) {
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Adoption adoption) {
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {
    adoptionRepository.deleteById(id);
  }
}
