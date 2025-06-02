package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseFileInfo {
    private final Long thumbMusicId;
    private final Long thumbnailId;
    private final Long innerImageId;

    public UniverseFileInfo(Long thumbMusicId, Long thumbnailId, Long innerImageId) {
        this.innerImageId = innerImageId;
        this.thumbMusicId = thumbMusicId;
        this.thumbnailId = thumbnailId;
    }
}
