package com.gzsaps.java.repository;

import com.gzsaps.java.controller.NoticeHandler;
import com.gzsaps.java.entity.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeRepositoryTest {
  @Autowired
  private NoticeHandler noticeHandler;

  @Test
  void delete() {
    noticeHandler.delete(1);
  }
}