package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.admin.domain.item.ItemType;

import java.util.List;

public record CreateItemMetadata(
        List<ItemData> items
) {
    public record ItemData(
            String name,
            ItemType itemType,
            SoundSourceData soundSourceData,
            CircleData circleData,
            RectangleData rectangleData,
            EllipseData ellipseData
    ) {

    }

    public record SoundSourceData(
            String form,
            String name,
            String description,
            Boolean active
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
