package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.car.Car;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class CarDAO {

    public List<Car> findFreeCars(int offset, int limit) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Car c WHERE c.car_status='FREE'");
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return (List<Car>) query.list();
        }
    }
    public List<Car> findFreeCars() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Car c WHERE c.car_status='FREE'");
            return (List<Car>) query.list();
        }
    }




    public Car findById(Long id){
        Car car=null;
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            car = session.get(Car.class, id);
        }
        return car;
    }

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

//    public List<Car> findAll() {
//        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
//            return (List<Car>)session.createQuery("from Car").list();
//        }
//    }
}
