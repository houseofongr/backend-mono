package com.hoo.aoo.admin.domain.universe.space;

import java.util.List;

public class TreeInfo {
    private final Integer depth;
    private final Space parentSpace;
    private final List<Space> childSpaces;
    private final List<Element> elements;

    private TreeInfo(Integer depth, Space parentSpace, List<Space> childSpaces, List<Element> elements) {
        this.depth = depth;
        this.parentSpace = parentSpace;
        this.childSpaces = childSpaces;
        this.elements = elements;
    }
}
