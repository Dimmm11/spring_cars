package com.example.demo.model.hibernateLearn.dao;

import com.example.demo.controller.securingWeb.HibernateSessionFactory;
import com.example.demo.model.entity.user.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO {

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


    public int save(UserEntity userEntity) {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()){
           return ((Long) session.save(userEntity)).intValue();
        }
    }
}
