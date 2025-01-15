package com.hoo.aoo.admin.application.service;

import java.util.List;

public record Metadata(
        House house,
        List<Room> rooms
) {
    public record House(
            String title,
            String author,
            String description,
            String houseForm,
            String borderForm,
            Float width,
            Float height
    ) {

    }
    public record Room(
            String form,
            String name,
            String originalName,
            String newName,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height
    ) {

    }
}
