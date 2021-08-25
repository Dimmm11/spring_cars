package com.example.demo.model.service;

import com.example.demo.exception.NoCarsFoundException;
import com.example.demo.model.entity.car.Car;
import com.example.demo.model.entity.car.CarComfort;
import com.example.demo.model.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private static final String CAR_NOT_FOUND = "Car not found with id: '%d'";
    private static final String NO_CARS_FOUND = "No cars found";

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private CarRepository carRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Car> findAllPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return carRepository.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<Car> findFreeCarsPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return carRepository.findFreeCars(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Car findCarById(long id) {
//        return carRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format(CAR_NOT_FOUND, id)));
        return carRepository.findById(id).orElseThrow(() -> new NoCarsFoundException(NO_CARS_FOUND));
    }

    @Override
    @Transactional
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public int updateCar(String marque, String model, CarComfort comfort, BigDecimal price, long id) {
        return carRepository.updateCar(marque, model, comfort, price,id);
    }

    @Override
    @Transactional
    public int deleteCar(long id) {
        carRepository.deleteById(id);
        return carRepository.rowCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Car> findFreeCars() {
        return carRepository.findFreeCars();
    }

    @Override
    @Transactional
    public int orderCar(Long id) {
        return carRepository.orderCar(id);
    }

    @Override
    @Transactional
    public int setCarFree(Long carId) {
        return carRepository.setCarFree(carId);
    }
}
