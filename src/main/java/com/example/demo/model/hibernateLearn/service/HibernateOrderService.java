package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.order.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface HibernateOrderService extends HibernateGenericService<Order>{

    int makeOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal totalCost, LocalDateTime time);

    List<Order> findByUserId(Long userId);

    List<Order> findByUserId(Long userId, int offset, int limit);

    int cancelOrder(Long orderId, Long carId);
}
