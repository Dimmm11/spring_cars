package com.example.demo.model.service;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService{
   UserEntity findById(long id);
   List<UserEntity> findAll();
   int updateUser(String username, String password, String email, Role role, long id);
   void deleteById(long id);
   UserEntity findByUsername(String username);
   void save(UserEntity userEntity);

   Page<UserEntity> findAll(int pageNo, int pageSize);
}
