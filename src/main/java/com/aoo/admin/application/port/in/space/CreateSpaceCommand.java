package com.aoo.admin.application.port.in.space;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record CreateSpaceCommand(
        Long universeId,
        Long parentSpaceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY,
        MultipartFile imageFile
) {
    public CreateSpaceCommand {
        if (universeId == null || parentSpaceId == null) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (title == null || title.isBlank() || title.length() > 100) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (description != null && description.length() > 5000) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (startX == null || startX < 0 || startX > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (startY == null || startY < 0 || startY > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (endX == null || endX < 0 || endX > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (endY == null || endY < 0 || endY > 1) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static CreateSpaceCommand from(CreateSpaceCommand command, MultipartFile imageFile) {
        if (imageFile == null) throw new AdminException(AdminErrorCode.SPACE_FILE_REQUIRED);
        if (imageFile.getSize() > 100 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        return new CreateSpaceCommand(
                command.universeId(),
                command.parentSpaceId(),
                command.title(),
                command.description(),
                command.startX(),
                command.startY(),
                command.endX(),
                command.endY(),
                imageFile
        );
    }
}
