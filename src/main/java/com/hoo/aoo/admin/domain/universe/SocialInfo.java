package com.hoo.aoo.admin.domain.universe;

import java.util.List;

public class SocialInfo {
    private final Long likeCount;
    private final Long viewCount;
    private final List<String> tags;
    private final Category category;

    public SocialInfo(Long likeCount, Long viewCount, List<String> tags, Category category) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.tags = tags;
        this.category = category;
    }
}
