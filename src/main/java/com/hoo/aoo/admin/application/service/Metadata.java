package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.domain.room.Room;

import java.util.List;

public record Metadata(
        House house,
        List<Room> rooms
) {
    public record House(
            String title,
            String author,
            String description,
            String houseFormName,
            String borderFormName,
            Integer width,
            Integer height
    ) {

    }
    public record Room(
            String formName,
            String name,
            Float x,
            Float y,
            Float z,
            Integer width,
            Integer height
    ) {

    }
}
