package com.tabor.notes.service;

import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void shouldNotSaveTheSameUserManyTimes() {
        //given
        final String email = "email@email.com";
        User user = User.of("marek", Role.ADMIN, email);
        //when
        userService.saveUser(user.getUsername(), user.getRole(), email);
        userService.saveUser(user.getUsername(), user.getRole(), email);
        userService.saveUser(user.getUsername(), user.getRole(), email);
        final List<User> all = userService.findAll();
        //then
        assertThat(all).hasSize(1);
    }
}