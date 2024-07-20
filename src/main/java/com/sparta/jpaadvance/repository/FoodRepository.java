package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.Food;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FoodRepository extends JpaRepository<Food, Long> {

}
