package com.example.demo.model.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import com.example.demo.model.entity.car.CarStatus;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    Car findCarById(long id);
    Car addCar(Car car);
    int updateCar(String marque, String model, CarComfort comfort, BigDecimal price, long id);
    void deleteCar(long id);
    List<Car> findAll();
    List<Car> findFreeCars();
    void orderCar(Long id);
    int setCarFree(Long carId);
}
