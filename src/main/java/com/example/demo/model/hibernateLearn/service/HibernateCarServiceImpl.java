package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.hibernateLearn.dao.CarDAO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class HibernateCarServiceImpl implements HibernateCarService {

    @Override
    public int orderCar(Long id) {
        return new CarDAO().orderCar(id);
    }

    @Override
    public int save(Car car) {
        return 0;
    }

    @Override
    public List<Car> findAll() {
        return new CarDAO().findFreeCars();
    }

    @Override
    public List<Car> findAll(int offset, int limit) {
        return new CarDAO().findFreeCars(offset,limit);
    }

    @Override
    public Car findById(Long id) {
        return new CarDAO().findById(id);
    }

    @Override
    public int update(Long id) {
        return 0;
    }

    @Override
    public int deleteById() {
        return 0;
    }
}
