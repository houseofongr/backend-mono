package com.hoo.aoo.admin.domain.universe;

import lombok.Getter;

import java.util.List;

@Getter
public class SocialInfo {
    private final Long likeCount;
    private final Long viewCount;
    private final List<String> hashtags;

    public SocialInfo(Long likeCount, Long viewCount, List<String> hashtags) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.hashtags = hashtags;
    }
}
