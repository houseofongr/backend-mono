package com.hoo.main.application.port.in.home;

public record QuerySoundSourceResult(
        String name,
        String description,
        String createdDate,
        String updatedDate,
        Long audioFileId
) {

}
