package com.example.demo.model.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import com.example.demo.model.entity.car.CarStatus;
import com.example.demo.model.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findCarById(long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public int updateCar(String marque, String model, CarComfort comfort, BigDecimal price, long id) {
        return carRepository.updateCar(marque, model, comfort, price,id);
    }

    @Override
    public void deleteCar(long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findFreeCars() {
        return carRepository.findFreeCars();
    }

    @Override
    public void orderCar(Long id) {
        carRepository.orderCar(id);
    }

    @Override
    public int setCarFree(Long carId) {
        return carRepository.setCarFree(carId);
    }
}
