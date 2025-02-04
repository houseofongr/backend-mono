package com.hoo.aoo.aar.application.port.in.home;

import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;

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
