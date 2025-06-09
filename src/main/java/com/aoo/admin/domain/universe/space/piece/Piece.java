package com.aoo.admin.domain.universe.space.piece;

import com.aoo.admin.domain.universe.BaseFileInfo;
import com.aoo.admin.domain.universe.DateInfo;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.PosInfo;
import com.aoo.admin.domain.universe.space.SpacePieceBasicInfo;
import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

import java.time.ZonedDateTime;

@Getter
public class Piece extends UniverseTreeComponent {
    private final BaseFileInfo fileInfo;
    private SpacePieceBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private PosInfo posInfo;

    private Piece(Long id, BaseFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Piece create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey) {
        return new Piece(
                id,
                new BaseFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                null,
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Piece loadTreeComponent(Long id, Long innerImageFileId, Long universeId, Long parentSpaceId,  String title, String description, Float sx, Float sy, Float ex, Float ey, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Piece(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Piece loadSingle(Long id, Long innerImageFileId, String title, String description, ZonedDateTime createdTime, ZonedDateTime updatedTime, Float sx, Float sy, Float ex, Float ey) {
        return new Piece(
                id,
                new BaseFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(null, null, title, description),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null);
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

    public void updatePosInfo(Float sx, Float sy, Float ex, Float ey) {
        this.posInfo = new PosInfo(
                sx != null ? sx : posInfo.getSx(),
                sy != null ? sy : posInfo.getSy(),
                ex != null ? ex : posInfo.getEx(),
                ey != null ? ey : posInfo.getEy());
    }
}
