package com.hoo.aoo.admin.application.port.in;

import java.util.List;

public record ReadHouseListResult(
        List<House> houses
) {
    public record House(
            Long id,
            String title,
            String author,
            String description,
            String createdDate,
            String updatedDate,
            Long imageId
    ) {

    }
}
