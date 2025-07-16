package com.hoo.main.application.port.in.home;

import java.util.List;

public record QueryItemSoundSourcesResult(
        String itemName,
        List<SoundSourceInfo> soundSources
) {

    public record SoundSourceInfo(
            Long id,
            String name,
            String description,
            String createdDate,
            String updatedDate
    ) {

    }
}
