package com.tabor.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import com.tabor.notes.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private JacksonTester<User> mapperUser;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldCreateUserWithLocationUri() throws Exception {
        User user = User.of("marek", Role.ADMIN, "email@email.com");

        given(userService.saveUser(anyString(), any(Role.class), anyString()))
                .willReturn(user);

        final MockHttpServletResponse response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapperUser.write(user).getJson()))
                .andExpect(status().isCreated()).andReturn().getResponse();

        assertThat(response.getHeaders("Location")).hasSize(1);
        assertThat(response.getHeaders("Location").get(0)).contains("/users/");
    }
}