package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
