package com.gzsaps.java.controller;

import com.gzsaps.java.entity.Project;
import com.gzsaps.java.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectHandler {
  @Autowired
  private ProjectRepository projectRepository;

  @GetMapping("/findAll")
  public List<Project> findAll() {
    return projectRepository.findAll();
  }

  @GetMapping("/find/{id}")
  public Project find(@PathVariable("id") Integer id) {
    return projectRepository.findById(id).get();
  }

  @PostMapping("/save")
  public String save(@RequestBody Project project) {
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
}
