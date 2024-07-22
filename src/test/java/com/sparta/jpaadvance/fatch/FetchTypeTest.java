package com.sparta.jpaadvance.fatch;

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
public class FetchTypeTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FoodRepository foodRepository;

    // 지연 로딩시 필요한 데이터 / 유저:2, 음식:5
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
        food4.setName("후라이드 치킨");
        food4.setPrice(15000);
        food4.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food4);

        Food food5 = new Food();
        food5.setName("고구마 피자");
        food5.setPrice(30000);
        food5.setUser(user2); // 외래 키(연관 관계) 설정
        foodList.add(food5);
        foodRepository.saveAll(foodList);
    }


    // 아보카도 피자 조회
    @Test
    @Transactional // 지연 로딩(FetchType.LAZY) 사용시 @Transactional 필수로 필요하다
    @DisplayName("아보카도 피자 조회")
    void test1() {
        Food food = foodRepository.findById(2L).orElseThrow(NullPointerException::new);

        System.out.println("food.getName() = " + food.getName());
        System.out.println("food.getPrice() = " + food.getPrice());
        // 즉시로딩(FetchType.EAGER)에서 getUser()조회시 findById()할 때 함께 가져온다
        // 지연로딩(FetchType.LAZY)에서 getUser()조회시 select를 한 번 더 해서 가져온다
        System.out.println("아보카도 피자를 주문한 회원 정보 조회");
        System.out.println("food.getUser().getName() = " + food.getUser().getName());
    }

    // Robbie 고객 조회
    @Test
    @Transactional // 지연 로딩(FetchType.LAZY) 사용시 @Transactional 필수로 필요하다
    @DisplayName("Robbie 고객 조회")
    void test2() {
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        // @OneToMany는 default가 LAZY이므로 조회 후 가져온다
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }

    // Robbbie 고객 조회 실패
    // 지연 로딩(FetchType.LAZY)에서 @Transactional 사용 안할 경우 에러
    @Test
    @DisplayName("Robbie 고객 조회 실패")
    void test3() {
        User user = userRepository.findByName("Robbie");
        System.out.println("user.getName() = " + user.getName());

        System.out.println("Robbie가 주문한 음식 이름 조회");
        for (Food food : user.getFoodList()) {
            System.out.println(food.getName());
        }
    }

}