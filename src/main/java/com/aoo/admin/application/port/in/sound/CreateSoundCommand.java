package com.aoo.admin.application.port.in.sound;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import org.springframework.web.multipart.MultipartFile;

public record CreateSoundCommand(
        Long pieceId,
        String title,
        String description,
        MultipartFile audioFile
) {
    public static CreateSoundCommand create(Long spaceId, String title, String description) {
        if (spaceId == null)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (title == null || title.isBlank() || title.length() > 100)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (description != null && description.length() > 5000)
            throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);

        return new CreateSoundCommand(spaceId, title, description, null);
    }

    public static CreateSoundCommand withAudioFile(CreateSoundCommand baseCommand, MultipartFile audioFile) {
        if (audioFile == null) throw new AdminException(AdminErrorCode.SOUND_FILE_REQUIRED);
        if (audioFile.getSize() > 100 * 1024 * 1024) throw new AdminException(AdminErrorCode.EXCEEDED_FILE_SIZE);

        return new CreateSoundCommand(
                baseCommand.pieceId(),
                baseCommand.title(),
                baseCommand.description(),
                audioFile
        );
    }
}
