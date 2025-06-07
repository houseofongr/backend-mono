package com.aoo.admin.domain.universe.space.element;

import com.aoo.admin.domain.universe.BaseBasicInfo;
import com.aoo.admin.domain.universe.BaseFileInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.PosInfo;
import com.aoo.admin.domain.universe.space.SpaceElementBasicInfo;
import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Element extends UniverseTreeComponent {
    private final BaseFileInfo fileInfo;
    private final SpaceElementBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;

    private Element(Long id, BaseFileInfo fileInfo, SpaceElementBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Element create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY) {
        return new Element(
                id,
                new BaseFileInfo(innerImageId),
                new SpaceElementBasicInfo(universeId, parentSpaceId, title, description),
                null,
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public static Element loadTreeComponent(Long id, Long universeId, Long parentSpaceId, Long innerImageFileId, Integer depth, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Element(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpaceElementBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }
}
