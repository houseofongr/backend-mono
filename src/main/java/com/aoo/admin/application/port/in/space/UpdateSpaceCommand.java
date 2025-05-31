package com.aoo.admin.application.port.in.space;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;

public record UpdateSpaceCommand(
        String title,
        String description,
        Float dx,
        Float dy,
        Float scaleX,
        Float scaleY
) {
    public UpdateSpaceCommand {
        if ((title != null && (title.isBlank() || title.length() > 100)) ||
            (description != null && description.length() > 5000) ||
            (dx != null && (dx < 0 || dx > 1)) ||
            (dy != null && (dy < 0 || dy > 1)) ||
            (scaleX != null && (scaleX < 0 || scaleX > 1)) ||
            (scaleY != null && (scaleY < 0 || scaleY > 1))
        ) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }
}
