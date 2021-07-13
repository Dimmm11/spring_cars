package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.car.Car;
import com.example.demo.model.hibernateLearn.dao.CarDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HibernateCarServiceImpl implements HibernateCarService {

    @Autowired
    public HibernateCarServiceImpl(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    private CarDAO carDAO;

    @Override
    public int orderCar(Long id) {
        return carDAO.orderCar(id);
    }

    @Override
    public int save(Car car) {
        return 0;
    }

    @Override
    public List<Car> findAll() {
        return carDAO.findFreeCars();
    }

    @Override
    public List<Car> findAll(int offset, int limit) {
        return carDAO.findFreeCars(offset,limit);
    }

    @Override
    public Car findById(Long id) {
        return carDAO.findById(id);
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
