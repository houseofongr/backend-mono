package com.hoo.aoo.admin.application.port.in.soundsource;

public record QuerySoundSourceResult(
        String name,
        String description,
        String createdDate,
        String updatedDate,
        Boolean isActive,
        Long audioFileId
) {
}
