package com.lucky.libManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucky.libManagement.entity.User;
import com.lucky.libManagement.enummodel.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
//    Optional<User> findByUsername(String email);
    Optional<User> findById(Long userId);
}
