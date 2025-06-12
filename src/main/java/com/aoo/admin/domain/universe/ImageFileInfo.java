package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class ImageFileInfo {

    private Long imageId;

    public ImageFileInfo(Long imageId) {
        this.imageId = imageId;
    }

    public void updateImage(Long imageId) {
        this.imageId = imageId;
    }
}
