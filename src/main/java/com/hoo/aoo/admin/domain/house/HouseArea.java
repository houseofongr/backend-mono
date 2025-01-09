package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseArea {

    private final Integer width;
    private final Integer height;

    public HouseArea(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }
}
