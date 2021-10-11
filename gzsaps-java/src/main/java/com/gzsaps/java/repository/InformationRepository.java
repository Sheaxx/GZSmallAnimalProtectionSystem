package com.gzsaps.java.repository;

import com.gzsaps.java.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information,Integer> {
}
