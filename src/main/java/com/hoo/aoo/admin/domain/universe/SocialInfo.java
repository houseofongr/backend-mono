package com.hoo.aoo.admin.domain.universe;

import lombok.Getter;

import java.util.List;

@Getter
public class SocialInfo {
    private final Integer likeCount;
    private final Long viewCount;
    private final List<String> hashtags;

    public SocialInfo(Integer likeCount, Long viewCount, List<String> hashtags) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.hashtags = hashtags;
    }
}
