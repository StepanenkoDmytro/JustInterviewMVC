package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
    Optional<User> findByEmail(String email);
}
