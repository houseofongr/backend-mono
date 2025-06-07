package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.universe.space.Space;
import com.aoo.admin.domain.universe.space.element.Element;
import lombok.Getter;

import java.util.List;

@Getter
public class TraversalComponents {
    private final Universe universe;
    private final List<Space> spaces;
    private final List<Element> elements;

    public TraversalComponents(Universe universe, List<Space> spaces, List<Element> elements) {
        this.universe = universe;
        this.spaces = spaces;
        this.elements = elements;
    }
}
