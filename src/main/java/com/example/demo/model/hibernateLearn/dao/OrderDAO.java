package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.order.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDAO {

    public int makeOrder(Long userId, Long carId, Boolean driver, BigDecimal term, BigDecimal totalCost, LocalDateTime time) {
        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("INSERT INTO user_order (user_id, car_id, driver, term, total_cost, order_status, local_date_time) " +
                    "VALUES (:userId, :carId, :driver, :term, :total_cost, 'CHECKING', :dateTime)");
            query.setParameter("userId", userId);
            query.setParameter("carId", carId);
            query.setParameter("driver", driver);
            query.setParameter("term", term);
            query.setParameter("total_cost", totalCost);
            query.setParameter("dateTime", time);
            result = query.executeUpdate();
            session.getTransaction().commit();
        }
        return result;
    }

    public List<Order> findByUserId(Long userId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
//            Query query = session.createQuery("from Order o where o.user.id = :userId");
            Query query = session.createQuery("from Order oo where oo.user.id = :userId");
//            Query query = session.createSQLQuery("select * from user_order  where user_id = :userId");
            query.setParameter("userId", userId);
            return  query.list();
        }
    }

    public List<Order> findByUserId(Long userId, int offset, int limit) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Order oo where oo.user.id = :userId");
//            Query query = session.createSQLQuery("select * from user_order  where user_id = :userId");
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            return  query.list();
        }
    }

    public int cancelOrder(Long orderId, Long carId) {
        int result = 0;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query orderQuery = session.createSQLQuery("delete from user_order o where o.id = :orderId");
            orderQuery.setParameter("orderId", orderId);
            Query carQuery = session.createSQLQuery("update car c set c.car_status = 'FREE' where c.id = :carId");
            carQuery.setParameter("carId", carId);
            result = orderQuery.executeUpdate() + carQuery.executeUpdate();
            session.getTransaction().commit();
        }
        return result;
    }
}
