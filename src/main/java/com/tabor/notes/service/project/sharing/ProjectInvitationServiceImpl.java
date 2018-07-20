package com.tabor.notes.service.project.sharing;

import com.tabor.notes.model.Project;
import com.tabor.notes.model.ProjectInvitation;
import com.tabor.notes.model.SharingProject;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.ProjectInvitationRepository;
import com.tabor.notes.service.ProjectService;
import com.tabor.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectInvitationServiceImpl implements ProjectInvitationService {
    private final UserService userService;
    private final ProjectInvitationRepository piRepository;
    private final MailSenderService mailSender;
    private final ProjectService projectService;

    @Autowired
    public ProjectInvitationServiceImpl(UserService userService, ProjectInvitationRepository piRepository, MailSenderService mailSender, ProjectService projectService) {
        this.userService = userService;
        this.piRepository = piRepository;
        this.mailSender = mailSender;
        this.projectService = projectService;
    }

    @Override
    public void share(SharingProject sharingProject, String appUrl) {
        final User user = userService.findByUsername(sharingProject.getReceiver());
        String token = "token";
        final Project project = projectService.findByTitleAndOwner(sharingProject.getProjectTitle(), sharingProject.getSender());
        if (project != null) {
            final ProjectInvitation invitation = ProjectInvitation.of(user, token, project);
            mailSender.sendEmail(token, user.getEmail(), appUrl);
            piRepository.save(invitation);
        }
    }
}
