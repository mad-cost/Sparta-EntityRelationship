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
public class OneToOneTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  FoodRepository foodRepository;

  // OneToOne 단방향 테스트
  @Test
  @Rollback(value = false) // 테스트에서는 @Transactional 에 의해 자동 rollback 됨으로 false 설정해준다.
  @DisplayName("1대1 단방향 테스트")
  void test1() {

    User user = new User();
    user.setName("Robbie");

    // 외래 키의 주인인 Food Entity의 user 필드에 user 객체를 추가해 준다.
    /* 외래 키 주인만이(Food Entity)외래 키 를 등록, 수정, 삭제할 수 있으며,
    주인이 아닌 쪽(UserEntity)은 오직 외래 키를 읽기만 가능합니다. */
    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);
    food.setUser(user); // 외래 키(연관 관계) 설정

    userRepository.save(user);
    foodRepository.save(food);
  }





}