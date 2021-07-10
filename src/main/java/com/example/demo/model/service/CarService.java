package com.example.demo.model.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    Car findCarById(long id);
    Car addCar(Car car);
    int updateCar(String marque, String model, CarComfort comfort, BigDecimal price, long id);
    int deleteCar(long id);
    List<Car> findAll();
    List<Car> findFreeCars();
    int orderCar(Long id);
    int setCarFree(Long carId);
    // pagination
    Page<Car> findAllPaginated(int pageNo, int pageSize);
    Page<Car> findFreeCarsPaginated(int pageNo, int pageSize);
}
