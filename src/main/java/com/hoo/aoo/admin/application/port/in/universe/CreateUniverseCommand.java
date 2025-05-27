package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public record CreateUniverseCommand(
        @NotBlank String title,
        String description,
        Long authorId,
        Category category,
        PublicStatus publicStatus,
        List<String> tags,
        Map<String, MultipartFile> fileMap
) {

    public CreateUniverseCommand {
        if (title == null || title.isBlank() || title.length() > 100) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (description != null && description.length() > 5000) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        if (tags.size() > 10 || String.join("", tags).length() > 500) throw new AdminException(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
    }

    public static CreateUniverseCommand from(CreateUniverseCommand baseCommand, Map<String, MultipartFile> fileMap) {
        if (!fileMap.containsKey("thumbnail") || fileMap.get("thumbnail").getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE);
        if (!fileMap.containsKey("thumbMusic") || fileMap.get("thumbMusic").getSize() > 2 * 1024 * 1024) throw new AdminException(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE);

        return new CreateUniverseCommand(
                baseCommand.title(),
                baseCommand.description(),
                baseCommand.authorId(),
                baseCommand.category(),
                baseCommand.publicStatus(),
                baseCommand.tags(),
                fileMap
        );
    }
}
