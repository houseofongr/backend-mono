package com.aoo.admin.domain.universe.space;

import com.aoo.admin.domain.universe.BaseBasicInfo;
import lombok.Getter;

@Getter
public class SpaceElementBasicInfo extends BaseBasicInfo {

    private final Long universeId;
    private final Long parentSpaceId;

    public SpaceElementBasicInfo(Long universeId, Long parentSpaceId, String title, String description) {
        super(title, description);
        this.universeId = universeId;
        this.parentSpaceId = parentSpaceId;
    }
}
