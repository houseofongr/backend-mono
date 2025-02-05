package com.hoo.aoo.aar.application.port.in.home;

import java.util.List;

public record QuerySoundSourcesPathResult(
        List<SoundSourcePathInfo> soundSources
) {
    public record SoundSourcePathInfo(
            String name,
            String description,
            String createdDate,
            String updatedDate,
            Long audioFileId,
            String homeName,
            Long homeId,
            String roomName,
            Long roomId,
            String itemName,
            Long itemId
    ) {

    }
}
