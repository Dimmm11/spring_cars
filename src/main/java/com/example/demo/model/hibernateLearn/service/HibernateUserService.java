package com.example.demo.model.hibernateLearn.service;

import com.example.demo.model.entity.user.UserEntity;


public interface HibernateUserService extends HibernateGenericService<UserEntity>{
    UserEntity findByUsername(String username);
}
