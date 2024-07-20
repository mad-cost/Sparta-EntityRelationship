package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private double price;

  @OneToMany
  // @OneToMany은 외래키를 Many 테이블에 보관해둔다 하지만, 조작은 One에서 가능하다
  @JoinColumn(name = "food_id") // users 테이블에 food_id 컬럼 생성
  private List<User> userList = new ArrayList<>();
}