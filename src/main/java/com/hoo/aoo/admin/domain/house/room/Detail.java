package com.hoo.aoo.admin.domain.house.room;

import lombok.Getter;

@Getter
public class Detail {

    private String name;

    public Detail(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name == null ? this.name : name;
    }
}
