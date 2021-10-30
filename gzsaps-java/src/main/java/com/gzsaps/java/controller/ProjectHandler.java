package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Project;
import com.gzsaps.java.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectHandler {
  @Autowired
  private ProjectRepository projectRepository;

  @GetMapping("/findAll/{page}/{size}")
  public Page<Project> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
    Pageable pageable = PageRequest.of(page-1,size);
    return projectRepository.findAll(pageable);
  }

  @GetMapping("/find/{id}")
  public Project find(@PathVariable("id") Integer id) {
    Optional<Project> result = projectRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      return null;
    }
  }

  @PostMapping("/save")
  public String save(@RequestBody Project project) {
    project.setCreatetime(new Date());
    project.setLastmodifiedtime(new Date());
    project.setApplicantlist("");
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Project project){
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") Integer id){
    projectRepository.deleteById(id);
  }

  @PutMapping("/apply/{username}/{id}")
  public int apply(@PathVariable("username") String username,@PathVariable("id") Integer id) {
    Project project = projectRepository.findById(id).get();
    String str = project.getApplicantlist();
    String[] list = str.split(",");//报名名单字符串转变为数组
    if (list[0].equals("") || list[0].equals(" ")) {
      list = new String[0];
    } //处理空字符串
    boolean result = false;
    for (int i = 0;i<list.length;i++) {
      if(list[i].equals(username)) {
        result = true;
        break;
      }//搜索是否已经存在该用户名
    }
    if (result == true) return 0;//该用户已经报名过
    String[] newList = new String[list.length+1];
    for (int i=0;i<list.length;i++) {
      newList[i] = list[i];
    }
    newList[list.length] = username;//新数组最后添加用户名
    StringBuilder res = new StringBuilder(newList[0]);
    for (int i=1;i<newList.length;i++) {
      res.append(",");
      res.append(newList[i]);
    }//数组转成字符串
    project.setApplicantlist(res.toString());
    projectRepository.save(project);
    return 1;
  }

  @PutMapping("/pass/{id}")
  public String pass(@PathVariable("id") Integer id) {
    Project project = projectRepository.findById(id).get();
    project.setStatus(1);
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/cutoff/{id}")
  public String cutoff(@PathVariable("id") Integer id) {
    Project project = projectRepository.findById(id).get();
    project.setStatus(2);
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/end/{id}")
  public String end(@PathVariable("id") Integer id) {
    Project project = projectRepository.findById(id).get();
    project.setStatus(3);
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @GetMapping("/showVolunteers/{id}")
  public String showVolunteers(@PathVariable("id") Integer id) {
    Project project = projectRepository.findById(id).get();
    return project.getApplicantlist();
  }
}
