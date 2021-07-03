package com.example.demo.model.entity.car;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2,max = 12,message = "length should be between 2 and 12 symbols")
    @NotBlank(message = "required data")
    private String marque;

    @Size(max=12, message = "max length 12 symbols")
    @NotBlank(message = "required data")
    private String model;

    @NotNull(message ="required data")
    @DecimalMin(value = "0.0", message = "price should be real")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CarComfort comfort;

    @Enumerated(EnumType.STRING)
    private CarStatus car_status;

}
