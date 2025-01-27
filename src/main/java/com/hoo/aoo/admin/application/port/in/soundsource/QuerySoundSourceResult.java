package com.hoo.aoo.admin.application.port.in.soundsource;

import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;

public record QuerySoundSourceResult(
        String name,
        String description,
        String createdDate,
        String updatedDate,
        Boolean isActive,
        Long audioFileId
) {

    public static QuerySoundSourceResult of(SoundSource soundSource) {
        return new QuerySoundSourceResult(
                soundSource.getSoundSourceDetail().getName(),
                soundSource.getSoundSourceDetail().getDescription(),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSource.getBaseTime().getCreatedTime()),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSource.getBaseTime().getUpdatedTime()),
                soundSource.getActive().isActive(),
                soundSource.getFile().getFileId().getId()
        );
    }
}
