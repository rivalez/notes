package com.tabor.notes.service;

import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public final class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(String username, Role role, String email) {
        return Optional.ofNullable(repository.findByUsername(username))
                .orElseGet(() -> repository.save(User.of(username, role, email)));
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(Long id) {
        final User blankUser = User.of("non existing user", Role.ADMIN, "non existing email");
        return repository.findById(id).orElse(blankUser);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
