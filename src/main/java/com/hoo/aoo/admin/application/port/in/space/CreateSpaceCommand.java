package com.hoo.aoo.admin.application.port.in.space;

import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record CreateSpaceCommand(
        Long universeId,
        Long parentSpaceId,
        String title,
        String description,
        Float dx,
        Float dy,
        Float scaleX,
        Float scaleY,
        MultipartFile imageFile
) {
    public CreateSpaceCommand {
        if (universeId == null || parentSpaceId == null) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (title == null || title.isBlank() || title.length() > 100) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (description != null && description.length() > 5000) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (dx == null || dx < 0 || dx > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (dy == null || dy < 0 || dy > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (scaleX == null || scaleX < 0 || scaleX > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (scaleY == null || scaleY < 0 || scaleY > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static CreateSpaceCommand from(CreateSpaceCommand command, MultipartFile imageFile) {
        if (imageFile == null || imageFile.getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.ILLEGAL_SPACE_IMAGE);
        return new CreateSpaceCommand(
                command.universeId(),
                command.parentSpaceId(),
                command.title(),
                command.description(),
                command.dx(),
                command.dy(),
                command.scaleX(),
                command.scaleY(),
                imageFile
        );
    }
}
