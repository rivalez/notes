package com.tabor.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabor.notes.Application;
import com.tabor.notes.model.Role;
import com.tabor.notes.model.SharingProject;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.ProjectInvitationRepository;
import com.tabor.notes.service.ProjectService;
import com.tabor.notes.service.UserService;
import com.tabor.notes.service.project.sharing.MailSenderService;
import com.tabor.notes.service.project.sharing.ProjectInvitationService;
import com.tabor.notes.service.project.sharing.ProjectInvitationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SharingControllerTest {
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;

    @MockBean
    private MailSenderService mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectInvitationRepository piRepository;

    private ProjectInvitationService piService;

    @Before
    public void setUp() {
        piService = new ProjectInvitationServiceImpl(userService, piRepository, mailSender, projectService);
        JacksonTester.initFields(this, new ObjectMapper());
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void shouldConfirmProjectSharing_thenAddtoUsersProjects() {
        //given
        final String sender = "Sender";
        userService.saveUser(sender, Role.ADMIN, "from@mail.com");
        final String projectTitle = "title";
        projectService.save(projectTitle, sender);

        final String receiver = "Receiver";
        userService.saveUser(receiver, Role.ADMIN, "to@mail.com");

        SharingProject sharingProject = new SharingProject(sender, receiver, projectTitle);
        HttpEntity<SharingProject> entity = new HttpEntity<>(sharingProject, new HttpHeaders());
        //send request of exchange
        restTemplate.exchange("http://localhost:" + port + "/share", HttpMethod.POST, entity, String.class);

        verify(mailSender).sendEmail(anyString(), anyString(), anyString());
        final User emptyReceiver = userService.findByUsername(receiver);
        assertThat(emptyReceiver.getProjects()).isEmpty();

        //confirm with token
        String token = "token";
        restTemplate.exchange("http://localhost:" + port + "/confirm?token=" + token, HttpMethod.GET, new HttpEntity<>(token), String.class);

        assertThat(userService.findByUsername(receiver).getProjects()).hasSize(1);
    }
}