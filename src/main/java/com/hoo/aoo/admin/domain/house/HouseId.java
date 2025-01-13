package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseId {

    private final String title;
    private final String author;
    private final String description;

    public HouseId(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }
}
