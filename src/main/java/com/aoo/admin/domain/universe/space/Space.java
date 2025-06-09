package com.aoo.admin.domain.universe.space;

import com.aoo.admin.domain.universe.BaseFileInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Space extends UniverseTreeComponent {
    private BaseFileInfo fileInfo;
    private SpacePieceBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private PosInfo posInfo;

    private Space(Long id, BaseFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Space create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY) {

        return new Space(
                id,
                new BaseFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                null,
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public static Space loadSingle(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Float dx, Float dy, Float scaleX, Float scaleY) {
        return new Space(
                id,
                new BaseFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public static Space loadTreeComponent(Long id, Long universeId, Long parentSpaceId, Long innerImageFileId, Integer depth, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Space(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy,scaleX,scaleY),
                null
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }

    public void updateBasicInfo(String title, String description) {
        this.basicInfo = new SpacePieceBasicInfo(
                this.basicInfo.getUniverseId(),
                this.basicInfo.getParentSpaceId(),
                title != null ? title : basicInfo.getTitle(),
                description != null ? description : basicInfo.getDescription()
        );
    }

    public void updatePosInfo(Float dx, Float dy, Float scaleX, Float scaleY) {
        this.posInfo = new PosInfo(
                dx != null ? dx : posInfo.getSx(),
                dy != null ? dy : posInfo.getSy(),
                scaleX != null ? scaleX : posInfo.getEx(),
                scaleY != null ? scaleY : posInfo.getEy());
    }

    public void updateInnerImage(Long innerImageId) {
        this.fileInfo = new BaseFileInfo(innerImageId);
    }
}
