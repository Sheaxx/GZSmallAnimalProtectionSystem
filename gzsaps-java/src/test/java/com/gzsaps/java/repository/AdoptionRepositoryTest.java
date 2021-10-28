package com.gzsaps.java.repository;

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

  @Test
  void findAll() {
    System.out.println(noticeRepository.findAll());
  }
}