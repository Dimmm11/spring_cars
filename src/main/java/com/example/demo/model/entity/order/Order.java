package com.example.demo.model.entity.order;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private Boolean driver;

    @Min(value = 1)
    private BigDecimal term;

    @Min(value = 0)
    private BigDecimal total_cost;

    @Enumerated(EnumType.STRING)
    private OrderStatus order_status;

    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime localDateTime;

}
