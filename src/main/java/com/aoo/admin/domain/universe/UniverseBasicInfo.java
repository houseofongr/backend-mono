package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseBasicInfo extends BaseBasicInfo {
    private Category category;
    private PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, Category category, PublicStatus publicStatus) {
        super(title, description);
        this.category = category;
        this.publicStatus = publicStatus;
    }

    public void updateUniverseInfo(String title, String description, Category category, PublicStatus publicStatus) {
        super.update(title, description);
        this.category = category != null ? category : this.category;
        this.publicStatus = publicStatus != null ? publicStatus : this.publicStatus;
    }

}
