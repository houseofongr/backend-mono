package com.hoo.aoo.admin.domain.universe;

import java.util.List;

public class SocialInfo {
    private final Long likeCount;
    private final Long viewCount;
    private final List<String> hashtags;
    private final Category category;

    public SocialInfo(Long likeCount, Long viewCount, List<String> hashtags, Category category) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.hashtags = hashtags;
        this.category = category;
    }
}
