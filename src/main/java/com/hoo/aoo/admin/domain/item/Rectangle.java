package com.hoo.aoo.admin.domain.item;

import lombok.Getter;

@Getter
public class Rectangle extends Shape {

    private final Float width;
    private final Float height;
    private final Float angle;

    public Rectangle(Float x, Float y, Float width, Float height, Float angle) {
        super(ItemType.RECTANGLE, x, y);
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

}
