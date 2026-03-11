package com.furniture.repository;

import com.furniture.entity.Order;
import com.furniture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findByUserId(Long userId);

    List<Order> findAllByOrderByCreatedAtDesc();
}
