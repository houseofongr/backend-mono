package com.hoo.aoo.admin.domain.universe.space;

import com.hoo.aoo.admin.domain.universe.BasicInfo;
import com.hoo.aoo.admin.domain.universe.DateInfo;

import java.util.List;

public class Element {
    private final Long id;
    private final Long imageId;
    private final List<Long> soundSourceIds;
    private final BasicInfo basicInfo;
    private final DateInfo dateInfo;

    private Element(Long id, Long imageId, List<Long> soundSourceIds, BasicInfo basicInfo, DateInfo dateInfo) {
        this.id = id;
        this.imageId = imageId;
        this.soundSourceIds = soundSourceIds;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
    }
}
