package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Adoption;
import com.gzsaps.java.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/adoption")
public class AdoptionHandler {
  @Autowired
  private AdoptionRepository adoptionRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<Adoption> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
    Pageable pageable = PageRequest.of(page-1,size);
    return adoptionRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public Adoption find(@PathVariable("id") Integer id) {
    return adoptionRepository.findById(id).get();
  }

  @PostMapping("/save")
  public String save(@RequestBody Adoption adoption) {
    adoption.setCreatetime(new Date());
    adoption.setLastmodifiedtime(new Date());
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Adoption adoption){
    adoption.setLastmodifiedtime(new Date());
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
