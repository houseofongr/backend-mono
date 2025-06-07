package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class BaseFileInfo {

    private final Long innerImageId;

    public BaseFileInfo(Long innerImageId) {
        this.innerImageId = innerImageId;
    }
}
