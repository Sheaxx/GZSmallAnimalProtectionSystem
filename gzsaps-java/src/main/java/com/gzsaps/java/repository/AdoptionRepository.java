package com.gzsaps.java.repository;

import com.gzsaps.java.entity.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption,Integer> {
}
