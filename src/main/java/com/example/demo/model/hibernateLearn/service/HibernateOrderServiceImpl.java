package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.hibernateLearn.dao.OrderDAO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class HibernateOrderServiceImpl implements HibernateOrderService{

    @Override
    public int cancelOrder(Long orderId, Long carId) {
        return new OrderDAO().cancelOrder(orderId,carId);
    }

    @Override
    public List<Order> findByUserId(Long userId, int offset, int limit) {
        return new OrderDAO().findByUserId(userId, offset, limit);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return new OrderDAO().findByUserId(userId);
    }

    @Override
    public int makeOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal totalCost, LocalDateTime time) {
        return new OrderDAO().makeOrder(userId, carId, driver,  term,  totalCost,  time);
    }

    @Override
    public int save(Order order) {
        return 0;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public int update(Long id) {
        return 0;
    }

    @Override
    public int deleteById() {
        return 0;
    }
}
