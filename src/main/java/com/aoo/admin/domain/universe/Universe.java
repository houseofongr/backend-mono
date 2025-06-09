package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.universe.space.TreeInfo;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Universe extends UniverseTreeComponent {
    private UniverseFileInfo fileInfo;
    private UniverseBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private SocialInfo socialInfo;

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

    public void updateBasicInfo(String title, String description, Long authorId, Category category, PublicStatus publicStatus) {
        String newTitle = title != null ? title : basicInfo.getTitle();
        String newDescription = description != null ? description : basicInfo.getDescription();
        Long newAuthorId = authorId != null ? authorId : basicInfo.getAuthorId();
        Category newCategory = category != null ? category : basicInfo.getCategory();
        PublicStatus newStatus = publicStatus != null ? publicStatus : basicInfo.getPublicStatus();

        this.basicInfo = new UniverseBasicInfo(newTitle, newDescription, newAuthorId, newCategory, newStatus);
    }

    public void updateSocialInfo(List<String> tags) {
        this.socialInfo = tags != null ? new SocialInfo(socialInfo.getLikeCount(), socialInfo.getViewCount(), tags) : this.socialInfo;
    }

    public void updateThumbnail(Long thumbnailId) {
        this.fileInfo = new UniverseFileInfo(this.fileInfo.getThumbMusicId(), thumbnailId, this.fileInfo.getInnerImageId());
    }

    public void updateThumbMusic(Long thumbMusicId) {
        this.fileInfo = new UniverseFileInfo(thumbMusicId, this.fileInfo.getThumbnailId(), this.fileInfo.getInnerImageId());
    }

    public void updateInnerImage(Long innerImageId) {
        this.fileInfo = new UniverseFileInfo(this.fileInfo.getThumbMusicId(), this.fileInfo.getThumbnailId(), innerImageId);
    }
}
