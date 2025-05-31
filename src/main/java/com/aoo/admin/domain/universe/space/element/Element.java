package com.aoo.admin.domain.universe.space.element;

import com.aoo.admin.domain.universe.BasicInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.space.PosInfo;

import java.util.List;

public class Element {
    private final Long id;
    private final Long imageId;
    private final List<Long> soundSourceIds;
    private final BasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;

    private Element(Long id, Long imageId, List<Long> soundSourceIds, BasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo) {
        this.id = id;
        this.imageId = imageId;
        this.soundSourceIds = soundSourceIds;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }
}
