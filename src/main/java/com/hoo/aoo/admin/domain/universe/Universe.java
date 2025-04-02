package com.hoo.aoo.admin.domain.universe;

import com.hoo.aoo.admin.domain.universe.space.TreeInfo;

public class Universe {
    private final Long id;
    private final Long thumbMusicId;
    private final Long thumbnailId;
    private final UniverseBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final SocialInfo socialInfo;
    private final TreeInfo treeInfo;

    private Universe(Long id, Long thumbMusicId, Long thumbnailId, UniverseBasicInfo basicInfo, DateInfo dateInfo, SocialInfo socialInfo, TreeInfo treeInfo) {
        this.id = id;
        this.thumbMusicId = thumbMusicId;
        this.thumbnailId = thumbnailId;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.socialInfo = socialInfo;
        this.treeInfo = treeInfo;
    }
}
