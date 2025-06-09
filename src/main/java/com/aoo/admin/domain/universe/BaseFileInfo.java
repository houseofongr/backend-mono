package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class BaseFileInfo {

    private Long innerImageId;

    public BaseFileInfo(Long innerImageId) {
        this.innerImageId = innerImageId;
    }

    public void updateInnerImage(Long innerImageId) {
        this.innerImageId = innerImageId;
    }
}
