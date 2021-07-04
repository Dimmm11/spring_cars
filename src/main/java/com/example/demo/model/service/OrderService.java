package com.example.demo.model.service;

import com.example.demo.model.entity.order.Order;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    void carOrder(Long userId,
                  Long carId,
                  Boolean driver,
                  BigDecimal term,
                  BigDecimal total_cost,
                  LocalDateTime localDateTime);

    List<Order> findByUser_Id(Long id);

    List<Order> findAll();

    int setOrderStatus(String orderStatus, Long orderId);

    Order getById( Long id);

    void deleteById(Long id);

    List<Order> findAllByUserAndStatus(String orderStatus,
                                       Long userId);

    void copyAndFinishOrder(Long orderId);
}
