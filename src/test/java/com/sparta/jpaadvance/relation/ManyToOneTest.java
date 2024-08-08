package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.Food;
import com.sparta.jpaadvance.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ManyToOneTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  FoodRepository foodRepository;

  // ManyToOne 단방향 테스트
  @Test
  @Rollback(value = false)
  @DisplayName("N대1 단방향 테스트")
  void test1() {
    User user = new User();
    user.setName("Robbie");

    User user2 = new User();
    user2.setName("Brad");

    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);
    food.setUser(user); // 외래 키(연관 관계) 설정

    Food food2 = new Food();
    food2.setName("양념 치킨");
    food2.setPrice(20000);
    food2.setUser(user); // 외래 키(연관 관계) 설정

    userRepository.save(user);
    foodRepository.save(food);
    foodRepository.save(food2);

    // 수업 외의 추가 내용
    Food food3 = new Food();
    food3.setName("양념 치킨");
    food3.setPrice(20000);
    food3.setUser(user2);

    food3.setUser(user2);
    userRepository.save(user2);
    foodRepository.save(food3);

  }



}