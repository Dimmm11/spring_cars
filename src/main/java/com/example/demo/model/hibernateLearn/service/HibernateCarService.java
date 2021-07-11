package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.car.Car;

public interface HibernateCarService extends HibernateGenericService<Car> {
    int orderCar(Long id);
}
