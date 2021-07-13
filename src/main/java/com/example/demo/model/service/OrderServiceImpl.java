package com.example.demo.model.service;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllByUserPaginated(Long userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository.findAllByUserPaginated(userId, pageable);
    }

    @Override
    @Transactional
    public int makeOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal total_cost, LocalDateTime localDateTime) {
       return orderRepository.makeOrder(userId, carId, driver, term, total_cost, localDateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUser_Id(Long id) {
        return orderRepository.findByUser_Id(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public int setOrderStatus(String orderStatus, Long orderId) {
        return orderRepository.setOrderStatus(orderStatus, orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        orderRepository.deleteById(id);
        return orderRepository.rowCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllByUser(Long userId){
        return orderRepository.findAllByUser(userId);
    }

    @Override
    @Transactional
    public int copyAndFinishOrder(Long orderId) {
        orderRepository.finishOrder(orderId);
        orderRepository.deleteById(orderId);
        return orderRepository.rowCount();
    }
}
