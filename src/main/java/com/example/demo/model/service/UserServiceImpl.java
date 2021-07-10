package com.example.demo.model.service;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<UserEntity> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public UserEntity findById(long id) {
//        return userRepository.findById(id).get();
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("no user by id:"+id));
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
    public int deleteById(long id) {
        userRepository.deleteById(id);
       return userRepository.rowCount();
    }

    @Override
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
       return userRepository.save(userEntity);
    }
}
