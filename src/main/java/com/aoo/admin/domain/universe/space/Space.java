package com.aoo.admin.domain.universe.space;

import com.aoo.admin.domain.universe.BaseFileInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.TreeInfo;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Space extends UniverseTreeComponent {
    private final DateInfo dateInfo;
    private final BaseFileInfo fileInfo;
    private final SpacePieceBasicInfo basicInfo;
    private final PosInfo posInfo;

    private Space(Long id, BaseFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Space create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey) {

        return new Space(
                id,
                new BaseFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                null,
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Space loadTreeComponent(Long id, Long innerImageFileId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Space(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Space loadSingle(Long id, Long innerImageFileId, String title, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Float sx, Float sy, Float ex, Float ey) {
        return new Space(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(null, null, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }

}
