package com.aoo.admin.domain.universe;

import com.aoo.admin.domain.user.User;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Universe extends UniverseTreeComponent {
    private final UniverseFileInfo fileInfo;
    private final UniverseBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final SocialInfo socialInfo;
    private final AuthorInfo authorInfo;

    private Universe(Long id, UniverseFileInfo fileInfo, UniverseBasicInfo basicInfo, DateInfo dateInfo, SocialInfo socialInfo, TreeInfo treeInfo, AuthorInfo authorInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.socialInfo = socialInfo;
        this.authorInfo = authorInfo;
    }

    public static Universe create(Long thumbMusicId, Long thumbnailId, Long innerImageId, String title, String description, Category category, PublicStatus publicStatus, List<String> tag, User author) {
        return new Universe(null,
                new UniverseFileInfo(thumbMusicId, thumbnailId, innerImageId),
                new UniverseBasicInfo(title, description, category, publicStatus),
                null,
                new SocialInfo(0, 0L, tag),
                null,
                author != null? new AuthorInfo(
                        author.getUserInfo().getId(),
                        author.getUserInfo().getNickname())
                        : null
        );
    }

    public static Universe load(Long id, Long thumbMusicId, Long thumbnailId, Long innerImageId, String title, String description, Category category, PublicStatus publicStatus, Integer likeCount, Long viewCount, List<String> tag, User author, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Universe(id,
                new UniverseFileInfo(thumbMusicId, thumbnailId, innerImageId),
                new UniverseBasicInfo(title, description, category, publicStatus),
                new DateInfo(createdTime, updatedTime),
                new SocialInfo(likeCount, viewCount, tag),
                null,
                new AuthorInfo(author.getUserInfo().getId(), author.getUserInfo().getNickname()));
    }

    public static Universe loadTreeComponent(Long id, Long thumbMusicId, Long thumbnailId, Long innerImageFileId) {
        return new Universe(id, new UniverseFileInfo(thumbMusicId, thumbnailId, innerImageFileId), null, null, null, null, null);
    }

    public Boolean isMine(Long userId) {
        return this.getAuthorInfo().getId().equals(userId);
    }
}
