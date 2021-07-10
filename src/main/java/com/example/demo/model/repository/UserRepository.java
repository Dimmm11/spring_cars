package com.example.demo.model.repository;

import com.example.demo.model.entity.user.UserEntity;
import com.example.demo.model.entity.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE UserEntity u SET u.username=?1, u.password=?2, u.email=?3, u.role=?4 WHERE u.id=?5")
    int updateUser(String username, String password, String email, Role role, long id);


    @Override
    Page<UserEntity> findAll(Pageable pageable);

    @Query(value = "SELECT ROW_COUNT()", nativeQuery = true)
    int rowCount();
}
