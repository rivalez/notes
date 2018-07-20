package com.tabor.notes.service.project.sharing;

import com.tabor.notes.model.ProjectInvitation;
import com.tabor.notes.model.Role;
import com.tabor.notes.model.SharingProject;
import com.tabor.notes.repository.ProjectInvitationRepository;
import com.tabor.notes.service.ProjectService;
import com.tabor.notes.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectInvitationServiceTestIT {

    @Autowired
    private ProjectInvitationService projectInvitationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @MockBean
    private MailSenderService mailSender;

    @Autowired
    private ProjectInvitationRepository piRepository;

    @Test
    public void shouldSendCreationLinkViaMail() {
        //given
        final String sender = "Marek";
        final String receiver = "Stefan";
        final String projectTitle = "projectTitle";
        userService.saveUser(sender, Role.ADMIN, "sender@email.com");
        userService.saveUser(receiver, Role.ADMIN, "receiver@email.com");
        projectService.save(projectTitle, sender);
        String appUrl = "urlToActivation";
        SharingProject sharingProject = new SharingProject(sender, receiver, projectTitle);
        //when
        projectInvitationService.share(sharingProject, appUrl);
        final Set<ProjectInvitation> invitations = piRepository.findByUser(userService.findByUsername(receiver));
        //then
        verify(mailSender).sendEmail(anyString(), anyString(), anyString());
        assertThat(invitations).hasSize(1);
    }

    @Test
    public void whenProjectNotFound_shouldNotSendMail() {
        //given
        final String sender = "Mailer";
        final String receiver = "Stefailer";
        final String projectTitle = "projectTitle";
        userService.saveUser(sender, Role.ADMIN, "sender@email.com");
        userService.saveUser(receiver, Role.ADMIN, "receiver@email.com");
        String appUrl = "urlToActivation";
        SharingProject sharingProject = new SharingProject(sender, receiver, projectTitle);
        //when
        projectInvitationService.share(sharingProject, appUrl);
        final Set<ProjectInvitation> invitations = piRepository.findByUser(userService.findByUsername(receiver));
        //then
        verify(mailSender, never()).sendEmail(anyString(), anyString(), anyString());
        assertThat(invitations).hasSize(0);
    }
}