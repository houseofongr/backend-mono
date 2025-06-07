package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseFileInfo extends BaseFileInfo {

    private final Long thumbMusicId;
    private final Long thumbnailId;

    public UniverseFileInfo(Long thumbMusicId, Long thumbnailId, Long innerImageId) {
        super(innerImageId);
        this.thumbMusicId = thumbMusicId;
        this.thumbnailId = thumbnailId;
    }
}
