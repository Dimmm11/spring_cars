package com.example.demo.model.service;

import com.example.demo.model.entity.user.Role;
import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    UserServiceImpl userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void findById() {
        assertThrows(RuntimeException.class, () ->
                userService.findById(1)
        );
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(4, 5);
        userService.findAll();
        verify(userRepository, times(1)).findAll();
        assertNotNull(userService.findAll());
        userService.findAll(5, 5);
        verify(userRepository, times(1)).findAll(pageable);
    }


    @Test
    void deleteById() {
        when(userRepository.rowCount()).thenReturn(1);
        int result = userService.deleteById(5L);
        verify(userRepository, times(1)).deleteById(5L);
        assertTrue(result > 0);
    }

    @Test
    void save() {
        UserEntity user = new UserEntity();
        when(userRepository.save(user)).then(returnsFirstArg());
        assertSame(user, userService.save(user));
    }

    @Test
    void updateUser() {
        when(userRepository.updateUser(anyString(), anyString(), anyString(), any(Role.class), anyLong()))
                .thenReturn(1);
        assertTrue(userService.updateUser("name", "pwd", "email", Role.USER, 1L) > 0);
    }

    @Test
    void findByUsername() {
        UserEntity user = new UserEntity();
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }
}