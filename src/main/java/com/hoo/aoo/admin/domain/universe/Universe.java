package com.hoo.aoo.admin.domain.universe;

import com.hoo.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;
import lombok.extern.java.Log;

import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Universe {
    private final Long id;
    private Long thumbMusicId;
    private Long thumbnailId;
    private UniverseBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private SocialInfo socialInfo;
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


    public static Universe create(Long id, Long thumbnailId, Long thumbMusicId, String title, String description, Category category, PublicStatus publicStatus,  List<String> tag) {
        return new Universe(id,
                thumbMusicId,
                thumbnailId,
                new UniverseBasicInfo(title, description, category, publicStatus),
                null,
                new SocialInfo(0, 0L, tag),
                null);
    }

    public static Universe load(Long id, Long thumbnailId, Long thumbMusicId, String title, String description, Category category, PublicStatus publicStatus, Integer likeCount, Long viewCount, List<String> tag, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Universe(id,
                thumbMusicId,
                thumbnailId,
                new UniverseBasicInfo(title, description, category, publicStatus),
                new DateInfo(createdTime,updatedTime),
                new SocialInfo(likeCount, viewCount, tag),
                null);
    }

    public void updateBasicInfo(String title, String description, Category category, PublicStatus publicStatus) {
        String newTitle = title != null? title : basicInfo.getTitle();
        String newDescription = description != null? description : basicInfo.getDescription();
        Category newCategory = category != null? category : basicInfo.getCategory();
        PublicStatus newStatus = publicStatus != null? publicStatus : basicInfo.getPublicStatus();

        this.basicInfo = new UniverseBasicInfo(newTitle, newDescription, newCategory, newStatus);
    }

    public void updateSocialInfo(List<String> tags) {
        this.socialInfo = tags != null? new SocialInfo(socialInfo.getLikeCount(), socialInfo.getViewCount(), tags) : this.socialInfo;
    }

    public void updateThumbnail(Long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }
    public void updateThumbMusic(Long thumbMusicId) {
        this.thumbMusicId = thumbMusicId;
    }
}
