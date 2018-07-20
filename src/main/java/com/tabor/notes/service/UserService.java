package com.tabor.notes.service;

import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;

import java.util.List;

public interface UserService {
    User saveUser(String username, Role role, String email);

    User findById(Long id);
    User findByUsername(String username);

    List<User> findAll();
}
