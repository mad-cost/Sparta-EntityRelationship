package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
