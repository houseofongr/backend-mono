package com.aoo.admin.application.port.in.universe;

import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.TreeInfo;
import com.aoo.admin.domain.universe.space.element.Piece;

import java.util.List;

public record TraversalUniverseResult(
        Long universeId,
        Long innerImageId,
        List<SpaceTreeInfo> spaces,
        List<PieceTreeInfo> pieces
) {

    public static TraversalUniverseResult of(TreeInfo root) {
        if (root.getUniverseTreeComponent() instanceof Universe universe)
            return new TraversalUniverseResult(
                    universe.getId(),
                    universe.getFileInfo().getInnerImageId(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                            .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                            .map(treeInfo -> PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList()
            );

        else return null;
    }

    public record SpaceTreeInfo(
            Long spaceId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Float startX,
            Float startY,
            Float endX,
            Float endY,
            Long createdTime,
            Long updatedTime,
            List<SpaceTreeInfo> spaces,
            List<PieceTreeInfo> pieces
    ) {

        public static SpaceTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Space space)
                return new SpaceTreeInfo(
                        space.getId(),
                        space.getBasicInfo().getParentSpaceId() == null? -1 : space.getBasicInfo().getParentSpaceId(),
                        space.getFileInfo().getInnerImageId(),
                        space.getTreeInfo().getDepth(),
                        space.getBasicInfo().getTitle(),
                        space.getBasicInfo().getDescription(),
                        space.getPosInfo().getSx(),
                        space.getPosInfo().getSy(),
                        space.getPosInfo().getEx(),
                        space.getPosInfo().getEy(),
                        space.getDateInfo().getCreatedTime().toEpochSecond(),
                        space.getDateInfo().getUpdatedTime().toEpochSecond(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                                .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                                .map(treeInfo -> PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList()
                );

            else return null;
        }
    }

    public record PieceTreeInfo(
            Long elementId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Float startX,
            Float startY,
            Float endX,
            Float endY,
            Long createdTime,
            Long updatedTime
    ) {

        public static PieceTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Piece piece)
                return new PieceTreeInfo(
                        piece.getId(),
                        piece.getBasicInfo().getParentSpaceId() == null? -1 : piece.getBasicInfo().getParentSpaceId(),
                        piece.getFileInfo().getInnerImageId(),
                        piece.getTreeInfo().getDepth(),
                        piece.getBasicInfo().getTitle(),
                        piece.getBasicInfo().getDescription(),
                        piece.getPosInfo().getSx(),
                        piece.getPosInfo().getSy(),
                        piece.getPosInfo().getEx(),
                        piece.getPosInfo().getEy(),
                        piece.getDateInfo().getCreatedTime().toEpochSecond(),
                        piece.getDateInfo().getUpdatedTime().toEpochSecond()
                );

            else return null;
        }
    }
}
