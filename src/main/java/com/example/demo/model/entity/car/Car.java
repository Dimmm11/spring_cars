package com.example.demo.model.entity.car;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 2,max = 12,message = "length should be between 2 and 12 symbols")
    @NotBlank(message = "required data")
    @Column(name = "marque")
    private String marque;

    @Size(max=12, message = "max length 12 symbols")
    @NotBlank(message = "required data")
    @Column(name = "model")
    private String model;

    @NotNull(message ="required data")
    @DecimalMin(value = "0.0", message = "price should be real")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "comfort")
    private CarComfort comfort;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status")
    private CarStatus car_status;

}
