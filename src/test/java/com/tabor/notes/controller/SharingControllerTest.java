package com.tabor.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabor.notes.model.SharingProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SharingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<SharingProject> mapper;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldConfirmProjectSharing_thenAddtoUsersProjects() throws Exception {
        //given
        SharingProject sharingProject = new SharingProject("Sender", "Receiver", "title");

        //when
        final MockHttpServletResponse response = mockMvc.perform(post("/share")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.write(sharingProject).getJson()))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();
        //then

    }
}