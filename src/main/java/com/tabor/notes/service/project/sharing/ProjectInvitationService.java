package com.tabor.notes.service.project.sharing;

import com.tabor.notes.model.SharingProject;

public interface ProjectInvitationService {
    void share(SharingProject sharingProject, String appUrl);

    void activate(String token);
}
