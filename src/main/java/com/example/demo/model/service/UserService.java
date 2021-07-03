package com.example.demo.model.service;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;

import java.util.List;


public interface UserService{
   UserEntity findById(long id);
   List<UserEntity> findAll();
   int updateUser(String username, String password, String email, Role role, long id);
   void deleteById(long id);
   UserEntity findByUsername(String username);
   void save(UserEntity userEntity);
}
