package com.hoo.aoo.admin.application.port.in.space;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record UpdateSpaceCommand(
        Long targetId,
        String title,
        String description,
        Float dx,
        Float dy,
        Float scaleX,
        Float scaleY,
        MultipartFile image
) {
    public UpdateSpaceCommand {
        if ((targetId == null || targetId <= 0) ||
            (title != null && (title.isBlank() || title.length() > 100)) ||
            (description != null && description.length() > 5000) ||
            (dx != null && (dx < 0 || dx > 1)) ||
            (dy != null && (dy < 0 || dy > 1)) ||
            (scaleX != null && (scaleX < 0 || scaleX > 1)) ||
            (scaleY != null && (scaleY < 0 || scaleY > 1))
        ) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static UpdateSpaceCommand from(UpdateSpaceCommand base, MultipartFile image) {
        if (image != null && image.getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.ILLEGAL_SPACE_IMAGE);
        return new UpdateSpaceCommand(
                base.targetId(),
                base.title(),
                base.description(),
                base.dx(),
                base.dy(),
                base.scaleX(),
                base.scaleY(),
                image
        );
    }
}
