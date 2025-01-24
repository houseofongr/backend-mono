package com.hoo.aoo.admin.domain.room;

import lombok.Getter;

@Getter
public class RoomName {

    private String name;

    public RoomName(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name == null ? this.name : name;
    }
}
