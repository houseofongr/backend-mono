package com.hoo.aoo.admin.domain;

import lombok.Getter;

@Getter
public class Axis {

    private final Short x;
    private final Short y;
    private final Short z;

    public Axis(Short x, Short y, Short z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
