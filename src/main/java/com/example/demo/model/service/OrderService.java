package com.example.demo.model.service;

import com.example.demo.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    int makeOrder(Long userId,
                   Long carId,
                   Boolean driver,
                   BigDecimal term,
                   BigDecimal total_cost,
                   LocalDateTime localDateTime);

    List<Order> findByUser_Id(Long id);

    List<Order> findAll();

    int setOrderStatus(String orderStatus, Long orderId);

    Order getById( Long id);

    int deleteById(Long id);

    List<Order> findAllByUser(Long userId);

    int copyAndFinishOrder(Long orderId);

    Page<Order> findAllByUserPaginated(Long userId, int pageNo, int pageSize);

    Page<Order> findAllPaginated(int pageNo, int pageSize);
}
