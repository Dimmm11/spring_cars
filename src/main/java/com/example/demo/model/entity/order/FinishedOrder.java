package com.example.demo.model.entity.order;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.user.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class FinishedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private BigDecimal term;

    private BigDecimal total_cost;


}
