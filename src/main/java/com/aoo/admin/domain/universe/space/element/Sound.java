package com.aoo.admin.domain.universe.space.element;

import com.aoo.admin.domain.universe.BasicInfo;
import com.aoo.admin.domain.universe.DateInfo;

public class Sound {
    private final Long id;
    private final Long audioId;
    private final BasicInfo basicInfo;
    private final DateInfo dateInfo;

    private Sound(Long id, Long audioId, BasicInfo basicInfo, DateInfo dateInfo) {
        this.id = id;
        this.audioId = audioId;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
    }
}
