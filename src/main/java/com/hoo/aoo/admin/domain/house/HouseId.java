package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class HouseId {

    private String title;
    private String author;
    private String description;

    public HouseId(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public void update(String title, String author, String description) {
        this.title = title == null? this.title : title;
        this.author = author == null? this.author : author;
        this.description = description == null? this.description : description;
    }
}
