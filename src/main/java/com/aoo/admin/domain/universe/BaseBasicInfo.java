package com.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class BaseBasicInfo {
    private final String title;
    private final String description;

    public BaseBasicInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
