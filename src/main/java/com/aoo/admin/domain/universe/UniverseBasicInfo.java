package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseBasicInfo extends BasicInfo{
    private final Long authorId;
    private final Category category;
    private final PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, Long authorId, Category category, PublicStatus publicStatus) {
        super(title, description);
        this.authorId = authorId;
        this.category = category;
        this.publicStatus = publicStatus;
    }
}
