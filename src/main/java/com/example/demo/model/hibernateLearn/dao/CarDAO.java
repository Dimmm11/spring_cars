package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.car.Car;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CarDAO {

    @Transactional
    public List<Car> findFreeCars(int offset, int limit) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Car c WHERE c.car_status='FREE'");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return (List<Car>) query.list();
        }
    }
    @Transactional(readOnly = true)
    public List<Car> findFreeCars() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Car c WHERE c.car_status='FREE'");
            return (List<Car>) query.list();
        }
    }

    @Transactional(readOnly = true)
    public Car findById(Long id){
        Car car=null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            car = session.get(Car.class, id);
        }
        return car;
    }

    @Transactional // do we need this here ????
    public int orderCar(Long id) {
        int result=0;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            session.beginTransaction();
            Query query = session.createSQLQuery("UPDATE Car SET car_status='ORDERED' WHERE id=:id");
            query.setParameter("id", id);
           result = query.executeUpdate();
            session.getTransaction().commit();
        }
        return result;
    }

}
