package com.example.demo.model.service;

import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.entity.user.Role;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserEntity findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public int updateUser(String username, String password, String email, Role role, long id) {
        return userRepository.updateUser(username, password, email, role, id);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
