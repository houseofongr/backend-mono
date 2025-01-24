package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.admin.domain.item.ItemType;

public record ItemData(
        Long id,
        String name,
        ItemType itemType,
        CircleData circleData,
        RectangleData rectangleData,
        EllipseData ellipseData
) {

    public record CircleData(
            Float x,
            Float y,
            Float radius
    ) {

    }

    public record RectangleData(
            Float x,
            Float y,
            Float width,
            Float height,
            Float rotation
    ) {

    }

    public record EllipseData(
            Float x,
            Float y,
            Float radiusX,
            Float radiusY,
            Float rotation
    ) {

    }
}
