package com.example.demo.model.service;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Override
    public void carOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal total_cost, LocalDateTime localDateTime) {
       orderRepository.carOrder(userId, carId, driver, term, total_cost, localDateTime);
    }

    @Override
    public List<Order> findByUser_Id(Long id) {
        return orderRepository.findByUser_Id(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public int setOrderStatus(String orderStatus, Long orderId) {
        return orderRepository.setOrderStatus(orderStatus, orderId);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseGet(null);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAllByUserAndStatus(String orderStatus, Long userId){
        return orderRepository.findAllByUserAndStatus(orderStatus, userId);
    }
}
