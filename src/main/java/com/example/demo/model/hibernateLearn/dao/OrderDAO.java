package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.order.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Component
public class OrderDAO {

    @Transactional
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

    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long userId) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
//            Query<Order> query = session.createQuery("from Order oo where oo.user.id = :userId", Order.class);
            Query query = session.createQuery("select o from Order o left join fetch o.user left join fetch o.car where o.user.id = :userId");
            query.setParameter("userId", userId);
            List list = query.getResultList();
       session.getTransaction().commit();


            return list;

        }
    }

    @Transactional(readOnly = true)
    public List<Order> findByUserId(Long userId, int offset, int limit) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Query query = session.createQuery("select o from Order o left join fetch o.user left join fetch o.car where o.user.id = :userId");
            query.setParameter("userId", userId);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.list();
        }
    }

    @Transactional
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
