package com.aoo.admin.domain.universe.space;

import lombok.Getter;

@Getter
public class PosInfo {
    private final Float sx;
    private final Float sy;
    private final Float ex;
    private final Float ey;

    public PosInfo(Float sx, Float sy, Float ex, Float ey) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }
}
