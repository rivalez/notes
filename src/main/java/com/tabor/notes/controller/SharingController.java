package com.tabor.notes.controller;

import com.tabor.notes.model.SharingProject;
import com.tabor.notes.service.project.sharing.ProjectInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SharingController {
    private final ProjectInvitationService piService;

    @Autowired
    public SharingController(ProjectInvitationService piService) {
        this.piService = piService;
    }

    @PostMapping("/share")
    public void shareWith(@RequestBody SharingProject sharingProject, HttpServletRequest request) {
        final String appUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        piService.share(sharingProject, appUrl);
    }

    @GetMapping("/confirm")
    public void confirm(@RequestParam("token") String token) {
        //findByToken in projectInvitation repository
        piService.activate(token);
    }
}
