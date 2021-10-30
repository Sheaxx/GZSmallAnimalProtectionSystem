package com.gzsaps.java.repository;

import com.gzsaps.java.controller.ProjectHandler;
import com.gzsaps.java.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdoptionRepositoryTest {
  @Autowired
  private AdoptionRepository adoptionRepository;
  @Autowired
  private ProjectRepository projectRepository;
  @Autowired
  private NoticeRepository noticeRepository;
  @Autowired
  private ProjectHandler projectHandler;

  @Test
  void findAll() {
    Project project = projectRepository.findById(1).get();
    project.setApplicantlist("aaa");
    projectRepository.save(project);
    System.out.println(projectHandler.apply("abc", 1));
  }
}