package com.sparta.jpaadvance.cascade;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import jakarta.persistence.CascadeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
/* 영속성 전이: 영속 상태의 Entity에서 수행되는 작업들이 연관된 Entity까지 전파되는 상황 */
public class CascadeTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  FoodRepository foodRepository;


  // Robbie 음식 주문 (영속성 사용 전)
  @Test
  @DisplayName("Robbie 음식 주문")
  void test1() {
    // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
    User user = new User();
    user.setName("Robbie");

    // 후라이드 치킨 주문
    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);

    user.addFoodList(food);
    
    // 양념 치킨 주문
    Food food2 = new Food();
    food2.setName("양념 치킨");
    food2.setPrice(20000);

    user.addFoodList(food2);

    /* 저장을 3번씩 하고 있는데, user만 저장해도,
     user와 연관된 데이터가 모두 저장 되도록 영속성 전이(Cascade)를 사용해보자 */
    userRepository.save(user);
    foodRepository.save(food);
    foodRepository.save(food2);
  }


  // 영속성 전이 저장
    /* CascadeType.PERSIST: Entity가 영속화 될 때 연관된 Entity도 자동으로 영속화되도록 하는 데 사용
      ex) UserEntity를 저장할 때, FoodEntity도 함께 저장된다 */
  @Test
  @DisplayName("영속성 전이 저장")
  void test2() {
    // 고객 Robbie 가 후라이드 치킨과 양념 치킨을 주문합니다.
    User user = new User();
    user.setName("Robbie");

    // 후라이드 치킨 주문
    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);

    user.addFoodList(food);

    Food food2 = new Food();
    food2.setName("양념 치킨");
    food2.setPrice(20000);

    user.addFoodList(food2);

    userRepository.save(user);
  }


  // Robbie 탈퇴 (영속성 사용 전)
  @Test
  @Transactional
  @Rollback(value = false)
  @DisplayName("Robbie 탈퇴")
  void test3() {
    // 고객 Robbie 를 조회합니다.
    User user = userRepository.findByName("Robbie");
    System.out.println("user.getName() = " + user.getName());

    // Robbie 가 주문한 음식 조회
    for (Food food : user.getFoodList()) {
      System.out.println("food.getName() = " + food.getName());
    }

    // 주문한 음식 데이터 삭제
    // user.getFoodList(): @OneToMany는 default가 LAZY(지연 로딩)이다
    // 지연 로딩(FetchType.LAZY) 사용시 @Transactional 필수로 필요하다
    foodRepository.deleteAll(user.getFoodList());

    // Robbie 탈퇴
    userRepository.delete(user);
  }


  // 영속성 전이 삭제
  // CascadeType.REMOVE
  @Test
  @Transactional
  @Rollback(value = false)
  @DisplayName("영속성 전이 삭제")
  void test4() {
    // 고객 Robbie 를 조회합니다.
    User user = userRepository.findByName("Robbie");
    System.out.println("user.getName() = " + user.getName());

    // Robbie 가 주문한 음식 조회
    for (Food food : user.getFoodList()) {
      System.out.println("food.getName() = " + food.getName());
    }

    // Robbie 탈퇴
    userRepository.delete(user);
  }

}