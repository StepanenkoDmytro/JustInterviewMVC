package com.ourapp.SuperAppHome.service;



import com.ourapp.SuperAppHome.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserByEmail(String email);
    User getUserById(long id);
    Optional<User> getUserOptionalByEmail(String email);
    void registration(User user);
    void saveUser(User user);
    void deleteUser(long id);
}
