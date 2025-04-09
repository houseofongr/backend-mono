package com.hoo.aoo.admin.domain.universe.space;

import com.hoo.aoo.admin.domain.universe.BasicInfo;
import com.hoo.aoo.admin.domain.universe.DateInfo;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Space {
    private final Long id;
    private final Long imageId;
    private final Long universeId;
    private final BasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;
    private final TreeInfo treeInfo;

    private Space(Long id, Long imageId, Long universeId, BasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        this.id = id;
        this.imageId = imageId;
        this.universeId = universeId;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
        this.treeInfo = treeInfo;
    }

    public static Space create(Long id, Long imageId, Long universeId, String title, String description, Float dx, Float dy, Float scaleX, Float scaleY, Space parentSpace) {
        Space newSpace = new Space(
                id,
                imageId,
                universeId,
                new BasicInfo(title, description),
                null,
                new PosInfo(dx, dy, scaleX, scaleY),
                parentSpace != null ? TreeInfo.createChild(parentSpace) : TreeInfo.createRoot()
        );

        if (!newSpace.isRoot()) parentSpace.getTreeInfo().addChild(newSpace);

        return newSpace;
    }

    public static Space loadSingle(Long id, Long imageId, Long universeId, String title, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Float dx, Float dy, Float scaleX, Float scaleY, Integer depth) {
        return new Space(
                id,
                imageId,
                universeId,
                new BasicInfo(title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy, scaleX, scaleY),
                TreeInfo.loadSingle(depth)
        );
    }

    public static Space loadAncestor(Long id, Long imageId, Long universeId, String title, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Float dx, Float dy, Float scaleX, Float scaleY, Space parent) {
        return new Space(id,
                imageId,
                universeId,
                new BasicInfo(title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(dx, dy, scaleX, scaleY),
                parent == null? TreeInfo.createRoot() : TreeInfo.createChild(parent));
    }

    public boolean isRoot() {
        return this.treeInfo.getDepth() == 1;
    }
}
