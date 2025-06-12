package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class SpacePieceBasicInfo extends BaseBasicInfo {

    private final Long universeId;
    private final Long parentSpaceId;

    public SpacePieceBasicInfo(Long universeId, Long parentSpaceId, String title, String description) {
        super(title, description);
        this.universeId = universeId;
        this.parentSpaceId = parentSpaceId == null? -1 : parentSpaceId;
    }

}
