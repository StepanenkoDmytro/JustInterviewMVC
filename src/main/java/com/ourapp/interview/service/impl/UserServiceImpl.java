package com.ourapp.SuperAppHome.service.impl;

import com.ourapp.SuperAppHome.model.user.Role;
import com.ourapp.SuperAppHome.model.user.Status;
import com.ourapp.SuperAppHome.model.user.User;
import com.ourapp.SuperAppHome.repository.RoleRepository;
import com.ourapp.SuperAppHome.repository.UserRepository;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email = %s not found", email)));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with id = %d not found", id)));
    }

    @Override
    public Optional<User> getUserOptionalByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registration(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");

        user.addRoleToUser(roleUser);
        user.setStatus(Status.ACTIVE);
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
