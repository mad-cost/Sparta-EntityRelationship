package com.sparta.jpaadvance.orphan;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
// 고아 Entity 삭제
public class OrphanTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  FoodRepository foodRepository;


  // 연관 관계 제거시 필요한 데이터 / 유저:2, 음식:5
  @Test
  @Transactional
  @Rollback(value = false)
  void init() {
    List<User> userList = new ArrayList<>();
    User user1 = new User();
    user1.setName("Robbie");
    userList.add(user1);

    User user2 = new User();
    user2.setName("Robbert");
    userList.add(user2);
    userRepository.saveAll(userList);

    List<Food> foodList = new ArrayList<>();
    Food food1 = new Food();
    food1.setName("고구마 피자");
    food1.setPrice(30000);
    food1.setUser(user1); // 외래 키(연관 관계) 설정
    foodList.add(food1);

    Food food2 = new Food();
    food2.setName("아보카도 피자");
    food2.setPrice(50000);
    food2.setUser(user1); // 외래 키(연관 관계) 설정
    foodList.add(food2);

    Food food3 = new Food();
    food3.setName("후라이드 치킨");
    food3.setPrice(15000);
    food3.setUser(user1); // 외래 키(연관 관계) 설정
    foodList.add(food3);

    Food food4 = new Food();
    food4.setName("양념 치킨");
    food4.setPrice(20000);
    food4.setUser(user2); // 외래 키(연관 관계) 설정
    foodList.add(food4);

    Food food5 = new Food();
    food5.setName("고구마 피자");
    food5.setPrice(30000);
    food5.setUser(user2); // 외래 키(연관 관계) 설정
    foodList.add(food5);
    foodRepository.saveAll(foodList);
  }


  // 연관 관계 제거
  @Test
  @Transactional
  @Rollback(value = false)
  @DisplayName("연관관계 제거")
  void test1() {
    // 고객 Robbie 를 조회합니다.
    User user = userRepository.findByName("Robbie");
    System.out.println("user.getName() = " + user.getName());

    // 연관된 음식 Entity 제거 : 후라이드 치킨
    Food chicken = null;
    for (Food food : user.getFoodList()) {
      if(food.getName().equals("후라이드 치킨")) {
        chicken = food;
      }
    }

    // user를 통하여 특정 food 삭제
    if(chicken != null) {
      user.getFoodList().remove(chicken);
    }

    // 연관 관계 제거 확인
    // Entity에서 orphanRemoval 등록해주기
    // orphanRemoval 사용 전: 터미널에는 제거된 것 처럼 출력이 보이지만, DB에는 제거되지 않은 모습을 볼 수 있다
    for (Food food : user.getFoodList()) {
      System.out.println("food.getName() = " + food.getName());
    }

    userRepository.save(user);
  }


}