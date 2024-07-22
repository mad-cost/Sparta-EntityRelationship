package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
  /* 반환 타입을 User로 설정함으로써 findByName()를 사용하는 곳에서
   Optional에 대한 .orElseThrow()를 설정하지 않아도 된다. */
  User findByName(String name);
}
