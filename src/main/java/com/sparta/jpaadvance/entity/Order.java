package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
/* orderDate필드를 사용하기 위해,
   @EntityListeners(AuditingEntityListener.class)등록 */
// + 추가적으로 SpringBootApplication에 @EnableJpaAuditing을 추가한다.
@EntityListeners(AuditingEntityListener.class)
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "food_id")
  private Food food;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime orderDate;
}