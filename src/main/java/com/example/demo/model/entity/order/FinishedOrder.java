package com.example.demo.model.entity.order;

import com.example.demo.model.entity.user.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FinishedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
