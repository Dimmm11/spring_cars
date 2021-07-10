package com.example.demo.model.service;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page<Order> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findAllByUserPaginated(Long userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository.findAllByUserPaginated(userId, pageable);
    }

    @Override
    public int makeOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal total_cost, LocalDateTime localDateTime) {
       orderRepository.makeOrder(userId, carId, driver, term, total_cost, localDateTime);
       return orderRepository.lastInsertedRow();
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
    public int deleteById(Long id) {
        orderRepository.deleteById(id);
        return orderRepository.rowCount();
    }

    @Override
    public List<Order> findAllByUser(Long userId){
        return orderRepository.findAllByUser(userId);
    }

    @Override
    public int copyAndFinishOrder(Long orderId) {
        orderRepository.finishOrder(orderId);
        orderRepository.deleteById(orderId);
        return orderRepository.rowCount();
    }
}
