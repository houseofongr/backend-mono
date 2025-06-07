package com.aoo.admin.application.port.in.universe;

import com.aoo.admin.domain.universe.Universe;
import com.aoo.admin.domain.universe.UniverseTreeComponent;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.TreeInfo;
import com.aoo.admin.domain.universe.space.element.Element;

import java.util.List;

public record TraversalUniverseResult(
        Long universeId,
        Long innerImageId,
        List<SpaceTreeInfo> spaces,
        List<ElementTreeInfo> elements
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
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Element)
                            .map(treeInfo -> ElementTreeInfo.of(treeInfo.getUniverseTreeComponent()))
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
            Float dx,
            Float dy,
            Float scaleX,
            Float scaleY,
            List<SpaceTreeInfo> spaces,
            List<ElementTreeInfo> elements
    ) {

        //TODO : Recursive 구현
        public static SpaceTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Space space)
                return new SpaceTreeInfo(
                        space.getId(),
                        space.getBasicInfo().getParentSpaceId(),
                        space.getFileInfo().getInnerImageId(),
                        space.getTreeInfo().getDepth(),
                        space.getBasicInfo().getTitle(),
                        space.getBasicInfo().getDescription(),
                        space.getPosInfo().getDx(),
                        space.getPosInfo().getDy(),
                        space.getPosInfo().getScaleX(),
                        space.getPosInfo().getScaleY(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                                .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Element)
                                .map(treeInfo -> ElementTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList()
                );

            else return null;
        }
    }

    public record ElementTreeInfo(
            Long elementId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Float dx,
            Float dy,
            Float scaleX,
            Float scaleY
    ) {

        public static ElementTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Element element)
                return new ElementTreeInfo(
                        element.getId(),
                        element.getBasicInfo().getParentSpaceId(),
                        element.getFileInfo().getInnerImageId(),
                        element.getTreeInfo().getDepth(),
                        element.getBasicInfo().getTitle(),
                        element.getBasicInfo().getDescription(),
                        element.getPosInfo().getDx(),
                        element.getPosInfo().getDy(),
                        element.getPosInfo().getScaleX(),
                        element.getPosInfo().getScaleY()
                );

            else return null;
        }
    }
}
