package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Universe extends UniverseTreeComponent {
    private final UniverseFileInfo fileInfo;
    private final UniverseBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final SocialInfo socialInfo;

    private Universe(Long id, UniverseFileInfo fileInfo, UniverseBasicInfo basicInfo, DateInfo dateInfo, SocialInfo socialInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.socialInfo = socialInfo;
    }

    public static Universe create(Long id, Long thumbMusicId, Long thumbnailId, Long innerImageId, Long authorId, String title, String description, Category category, PublicStatus publicStatus, List<String> tag) {
        return new Universe(id,
                new UniverseFileInfo(thumbMusicId, thumbnailId, innerImageId),
                new UniverseBasicInfo(title, description, authorId, category, publicStatus),
                null,
                new SocialInfo(0, 0L, tag),
                null);
    }

    public static Universe load(Long id, Long thumbMusicId, Long thumbnailId, Long innerImageId, Long authorId, String title, String description, Category category, PublicStatus publicStatus, Integer likeCount, Long viewCount, List<String> tag, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Universe(id,
                new UniverseFileInfo(thumbMusicId, thumbnailId, innerImageId),
                new UniverseBasicInfo(title, description, authorId, category, publicStatus),
                new DateInfo(createdTime, updatedTime),
                new SocialInfo(likeCount, viewCount, tag),
                null);
    }

    public static Universe loadTreeComponent(Long id, Long innerImageFileId) {
        return new Universe(id, new UniverseFileInfo(null, null, innerImageFileId), null, null, null, null);
    }

}
