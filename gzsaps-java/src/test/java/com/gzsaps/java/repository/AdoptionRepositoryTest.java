package com.gzsaps.java.repository;

import com.gzsaps.java.controller.AdoptionHandler;
import com.gzsaps.java.controller.CommentHandler;
import com.gzsaps.java.controller.ProjectHandler;
import com.gzsaps.java.entity.Adoption;
import com.gzsaps.java.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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
  @Autowired
  private CommentHandler commentHandler;
  @Autowired
  private AdoptionHandler adoptionHandler;

  @Test
  void findAll() {
    Project project = projectRepository.findById(1).get();
    project.setApplicantlist("aaa");
    projectRepository.save(project);
    System.out.println(projectHandler.apply("abc", 1));
  }

  @Test
  void commment() {
    Adoption adoption = new Adoption();
    adoption.setTitle("oooo");
    adoption.setContent("hhhhhh");
    adoption.setStatus(0);
    adoption.setCreatetime(new Date());
    adoption.setLastmodifiedtime(new Date());
    adoption.setAuthor("aaa");
    System.out.println(adoptionRepository.save(adoption));
  }
}