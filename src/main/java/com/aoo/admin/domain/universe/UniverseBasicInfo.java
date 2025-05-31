package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class UniverseBasicInfo extends BasicInfo{
    private final Category category;
    private final PublicStatus publicStatus;

    public UniverseBasicInfo(String title, String description, Category category, PublicStatus publicStatus) {
        super(title, description);
        this.category = category;
        this.publicStatus = publicStatus;
    }
}
