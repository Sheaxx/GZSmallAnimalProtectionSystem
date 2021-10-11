package com.gzsaps.java.repository;

import com.gzsaps.java.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
