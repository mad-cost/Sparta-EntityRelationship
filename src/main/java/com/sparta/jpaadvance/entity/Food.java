package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "food")
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private double price;

  // 지연 로딩 (N:1)
  @ManyToOne// @ManyToOne는 default가 FetchType.EAGER
  @JoinColumn(name = "user_id")
  private User user;


//  N:M 관계의 중간 테이블 Order 사용
  //@OneToMany(mappedBy = "food")
  //private List<Order> orderList = new ArrayList<>();
}