package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Adoption;
import com.gzsaps.java.repository.AdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adoption")
public class AdoptionHandler {
  @Autowired
  private AdoptionRepository adoptionRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<Adoption> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {//查找全部领养信息
    Pageable pageable = PageRequest.of(page-1,size);
    return adoptionRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public Adoption find(@PathVariable("id") Integer id) {//查找某个领养信息
    Optional<Adoption> result = adoptionRepository.findById(id);
    if(result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  @PostMapping("/save")
  public Integer save(@RequestBody Adoption adoption) {//添加领养信息
    adoption.setCreatetime(new Date());
    adoption.setLastmodifiedtime(new Date());
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return result.getAdoptionid();
    } else {
      return null;
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Adoption adoption){//修改领养信息
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id) {//删除领养信息
    adoptionRepository.deleteById(id);
  }

  @PutMapping("/adopted/{id}")
  public String adopted(@PathVariable("id") Integer id) {//将某条领养信息的状态更新为“已领养”
    Adoption adoption = adoptionRepository.findById(id).get();
    adoption.setStatus(1);
    Adoption result = adoptionRepository.save(adoption);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @GetMapping("/userAdd/{username}")
  public List<Adoption> userAdd(@PathVariable("username") String username) {//查找用户发布的领养信息
    List<Adoption> adoptions = adoptionRepository.findAll();
    List<Adoption> result = new ArrayList<>();
    for (int i=0;i<adoptions.size();i++) {
      if (adoptions.get(i).getAuthor().equals(username)) {
        result.add(adoptions.get(i));
      }
    }
    return result;
  }

  @GetMapping("/callback")
  public List<Integer> callback() {//管理员回访提示
    List<Adoption> adoptions = adoptionRepository.findAll();
    List<Integer> result = new ArrayList<>();
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
    String today = df.format(new Date());
    for (int i=0;i<adoptions.size();i++) {
      Integer adoptionid = adoptions.get(i).getAdoptionid();
      Date adopttime = adoptions.get(i).getAdopttime();
      if (adopttime != null) {
        if (df.format(new Date(adopttime.getTime() + 30 * 24 * 60 * 60 * 1000)).equals(today)){//一个月
          result.add(adoptionid);
        } else  if(df.format(new Date(adopttime.getTime() + 90 * 24 * 60 * 60 * 1000)).equals(today)){//三个月
          result.add(adoptionid);
        } else if (df.format(new Date(adopttime.getTime() + 182 * 24 * 60 * 60 * 1000)).equals(today)) {//半年
          result.add(adoptionid);
        } else if(df.format(new Date(adopttime.getTime() + 365 * 24 * 60 * 60 * 1000)).equals(today)) {//一年
          result.add(adoptionid);
        }
      }
    }
    return result;
  }
}
