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
    private final TokenGenerator tokenGenerator;

    @Autowired
    public ProjectInvitationServiceImpl(UserService userService, ProjectInvitationRepository piRepository, MailSenderService mailSender, ProjectService projectService, TokenGenerator tokenGenerator) {
        this.userService = userService;
        this.piRepository = piRepository;
        this.mailSender = mailSender;
        this.projectService = projectService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public void share(SharingProject sharingProject, String appUrl) {
        final User user = userService.findByUsername(sharingProject.getReceiver());
        String token = tokenGenerator.generate();
        final Project project = projectService.findByTitleAndOwner(sharingProject.getProjectTitle(), sharingProject.getSender());
        if (project != null) {
            final ProjectInvitation invitation = ProjectInvitation.of(user, token, project);
            mailSender.sendEmail(token, user.getEmail(), appUrl);
            piRepository.save(invitation);
        }
    }

    @Override
    public void activate(String token) {
        final ProjectInvitation toActivate = piRepository.findByToken(token);
        if (toActivate != null) {
            final User user = toActivate.getUser();
            user.addProject(toActivate.getProject());
            user.removeProjectInvitation(toActivate);
            userService.update(user);
        }
    }
}
