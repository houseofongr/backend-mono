package com.hoo.aoo.aar.application.port.in.home;

import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;

public record QuerySoundSourceResult(
        String name,
        String description,
        String createdDate,
        String updatedDate,
        Long audioFileId
) {

}
