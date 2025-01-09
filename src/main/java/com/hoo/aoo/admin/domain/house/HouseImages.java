package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseImages {

    private final Long borderImageId;
    private final Long basicImageId;

    public HouseImages(Long basicImageId, Long borderImageId) {
        this.basicImageId = basicImageId;
        this.borderImageId = borderImageId;
    }
}
