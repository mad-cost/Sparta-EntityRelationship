package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  // 지연 로딩 (N:1)
  // @OneToMany는 default가 LAZY(지연 로딩)이다
  @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private List<Food> foodList = new ArrayList<>();

  public void addFoodList(Food food) {
    this.foodList.add(food);
    food.setUser(this); // 외래 키 설정
  }


//  N:M 관계의 중간 테이블 Order 사용
  //@OneToMany(mappedBy = "user")
  //private List<Order> orderList = new ArrayList<>();
}