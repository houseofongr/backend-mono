package com.hoo.aoo.admin.domain.universe.space;

import lombok.Getter;

@Getter
public class PosInfo {
    private final Float dx;
    private final Float dy;
    private final Float scaleX;
    private final Float scaleY;

    public PosInfo(Float dx, Float dy, Float scaleX, Float scaleY) {
        this.dx = dx;
        this.dy = dy;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
}
