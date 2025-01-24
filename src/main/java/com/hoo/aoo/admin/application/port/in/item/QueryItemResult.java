package com.hoo.aoo.admin.application.port.in.item;

import java.util.List;

public record QueryItemResult(
        String itemName,
        List<SoundSourceInfo> soundSource
) {
    public record SoundSourceInfo(
            Long id,
            String name,
            String description,
            String createdDate,
            String updatedDate,
            Long audioFileId
    ) {

    }
}
