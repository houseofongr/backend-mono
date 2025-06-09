package com.aoo.admin.domain.universe.space.piece;

import com.aoo.admin.domain.universe.BaseBasicInfo;
import com.aoo.admin.domain.universe.DateInfo;

public class Sound {
    private final Long id;
    private final Long audioId;
    private final BaseBasicInfo baseBasicInfo;
    private final DateInfo dateInfo;

    private Sound(Long id, Long audioId, BaseBasicInfo baseBasicInfo, DateInfo dateInfo) {
        this.id = id;
        this.audioId = audioId;
        this.baseBasicInfo = baseBasicInfo;
        this.dateInfo = dateInfo;
    }
}
