package com.example.demo.model.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import com.example.demo.model.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    @Autowired
    CarRepository carRepository;

    // pagination
    @Override
    public Page<Car> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return carRepository.findAll(pageable);
    }
    @Override
    public Page<Car> findFreeCarsPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return carRepository.findFreeCars(pageable);
    }

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
    public int deleteCar(long id) {
        carRepository.deleteById(id);
        return carRepository.rowCount();
    }

    @Override
    public List<Car> findFreeCars() {
        return carRepository.findFreeCars();
    }

    @Override
    public int orderCar(Long id) {
        return carRepository.orderCar(id);
    }

    @Override
    public int setCarFree(Long carId) {
        return carRepository.setCarFree(carId);
    }
}
