package com.aoo.admin.application.port.in.universe;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Category;
import com.aoo.admin.domain.universe.PublicStatus;

import java.util.List;

public record UpdateUniverseCommand(
        String title,
        String description,
        Long authorId,
        Category category,
        PublicStatus publicStatus,
        List<String> hashtags
) {

    public UpdateUniverseCommand {
        if (title != null && (title.isBlank() || title.length() > 100))
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (description != null && description.length() > 5000)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if ((hashtags != null && !hashtags.isEmpty()) && (hashtags.size() > 10 || String.join("", hashtags).length() > 500))
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

}
