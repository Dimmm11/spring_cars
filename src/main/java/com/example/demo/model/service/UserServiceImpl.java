package com.example.demo.model.service;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("no user by id:"+id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public int updateUser(String username, String password, String email, Role role, long id) {
        return userRepository.updateUser(username, password, email, role, id);
    }

    @Override
    @Transactional
    public int deleteById(long id) {
        userRepository.deleteById(id);
       return userRepository.rowCount();
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
       return userRepository.save(userEntity);
    }
}
