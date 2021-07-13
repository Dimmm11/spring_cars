package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.user.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDAO {

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username){
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
            Query query = session.createQuery("from UserEntity u where u.username= :username");
            query.setParameter("username", username);
            if(!query.list().isEmpty()){
                return (UserEntity) query.list().get(0);
            }
            return null;
        }
    }

    @Transactional
    public int save(UserEntity userEntity) {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
           return ((Long) session.save(userEntity)).intValue();
        }
    }
}
