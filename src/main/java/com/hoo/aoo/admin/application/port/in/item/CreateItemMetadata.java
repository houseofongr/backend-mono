package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.admin.domain.item.ItemType;

import java.util.List;

public record CreateItemMetadata(
    List<ItemData> items
) {
    public record ItemData(
            String form,
            String name,
            ItemType itemType,
            CircleData circleData,
            RectangleData rectangleData,
            EllipseData ellipseData
    ) {

    }

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
            Float angle
    ) {

    }

    public record EllipseData(
            Float x,
            Float y,
            Float width,
            Float height,
            Float angle
    ) {

    }
}
