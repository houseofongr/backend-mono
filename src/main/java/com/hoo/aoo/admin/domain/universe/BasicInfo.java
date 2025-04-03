package com.hoo.aoo.admin.domain.universe;

import lombok.Getter;

@Getter
public class BasicInfo {
    private final String title;
    private final String description;

    public BasicInfo(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
