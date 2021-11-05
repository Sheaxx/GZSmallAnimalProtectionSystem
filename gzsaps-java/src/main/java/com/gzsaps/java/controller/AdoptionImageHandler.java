package com.gzsaps.java.controller;

import com.gzsaps.java.entity.AdoptionImage;
import com.gzsaps.java.repository.AdoptionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adoptionImage")
public class AdoptionImageHandler {

  @Autowired
  private AdoptionImageRepository adoptionImageRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<AdoptionImage> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {//查找所有领养信息图片
    Pageable pageable = PageRequest.of(page-1,size);
    return adoptionImageRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public AdoptionImage find(@PathVariable("id") Integer id) {//查找该领养信息下的图片
    List<AdoptionImage> list = adoptionImageRepository.findAll();
    for (int i=0;i<list.size();i++) {
      if (list.get(i).getAdoptionid() == id) {
        return list.get(i);
      }
    }
    return null;
  }

  @PostMapping("/save")
  public String save(@RequestBody AdoptionImage image) {//保存图片
    AdoptionImage result = adoptionImageRepository.save(image);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody AdoptionImage image) {//修改图片
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
