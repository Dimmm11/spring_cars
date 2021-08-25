package com.example.demo.model.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    @Test
    void findAllTest() {
        when(carRepository.findAll()).thenReturn(new ArrayList<>());
        List<Car> cars = carServiceImpl.findAll();
        Assertions.assertThat(cars).isNotNull();
    }

    @Test
    void addCarTest() {
        when(carServiceImpl.addCar(any(Car.class))).thenReturn(new Car());
        Car carToAdd = new Car();
        Car addedCar = carServiceImpl.addCar(carToAdd);
        Assertions.assertThat(addedCar).isInstanceOf(Car.class);
    }




}
