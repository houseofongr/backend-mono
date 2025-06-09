package com.aoo.admin.domain.universe.space.element;

import com.aoo.admin.domain.universe.BaseFileInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.PosInfo;
import com.aoo.admin.domain.universe.space.SpacePieceBasicInfo;
import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Piece extends UniverseTreeComponent {
    private final BaseFileInfo fileInfo;
    private final SpacePieceBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;

    private Piece(Long id, BaseFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Piece create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY) {
        return new Piece(
                id,
                new BaseFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                null,
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public static Piece loadTreeComponent(Long id, Long innerImageFileId, Long universeId, Long parentSpaceId,  String title, String description, Float dx, Float dy, Float scaleX, Float scaleY, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Piece(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy, scaleX, scaleY),
                null
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }
}
