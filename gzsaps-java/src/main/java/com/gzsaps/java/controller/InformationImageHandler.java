package com.gzsaps.java.controller;

import com.gzsaps.java.entity.InformationImage;
import com.gzsaps.java.repository.InformationImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/informationImage")
public class InformationImageHandler {
  @Autowired
  private InformationImageRepository informationImageRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<InformationImage> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
    Pageable pageable = PageRequest.of(page-1,size);
    return informationImageRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public InformationImage find(@PathVariable("id") Integer id) {//查找该领养信息下的图片
    List<InformationImage> list = informationImageRepository.findAll();
    for (int i=0;i<list.size();i++) {
      if (list.get(i).getInformationid() == id) {
        return list.get(i);
      }
    }
    return null;
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
