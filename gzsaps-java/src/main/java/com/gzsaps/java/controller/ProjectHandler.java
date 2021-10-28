package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Project;
import com.gzsaps.java.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    return projectRepository.findById(id).get();
  }

  @PostMapping("/save")
  public String save(@RequestBody Project project) {
    project.setCreatetime(new Date());
    project.setLastmodifiedtime(new Date());
    Project result = projectRepository.save(project);
    if(result != null) {
      return "success";
    } else {
      return "error";
    }
  }

  @PutMapping("/update")
  public String update(@RequestBody Project project){
    project.setLastmodifiedtime(new Date());
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
}
