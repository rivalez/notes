package com.tabor.notes.service;

import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;

public interface UserService {
    void saveUser(String username, Role role);

    User findByUsername(String username);
}
