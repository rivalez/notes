package com.tabor.notes.service;

import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveUser(String username, Role role) {
        repository.save(User.of(username, role));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
