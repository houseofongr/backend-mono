package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseBasicInfo extends BaseBasicInfo {
    private Long authorId;
    private Category category;
    private PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, Long authorId, Category category, PublicStatus publicStatus) {
        super(title, description);
        this.authorId = authorId;
        this.category = category;
        this.publicStatus = publicStatus;
    }

    public void updateUniverseInfo(String title, String description, Long authorId, Category category, PublicStatus publicStatus) {
        super.update(title, description);
        this.authorId = authorId != null ? authorId : this.authorId;
        this.category = category != null ? category : this.category;
        this.publicStatus = publicStatus != null ? publicStatus : this.publicStatus;
    }

}
