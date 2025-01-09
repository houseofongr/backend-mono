package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseId {

    private final String title;

    public HouseId(String title) {
        this.title = title;
    }
}
