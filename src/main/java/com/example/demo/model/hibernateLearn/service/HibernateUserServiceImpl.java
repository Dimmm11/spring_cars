package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.hibernateLearn.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class HibernateUserServiceImpl implements HibernateUserService{

    @Autowired
    public HibernateUserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private UserDAO userDAO;

    @Override
    public UserEntity findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public int save(UserEntity userEntity) {
        return userDAO.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public List<UserEntity> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public UserEntity findById(Long id) {
        return null;
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
