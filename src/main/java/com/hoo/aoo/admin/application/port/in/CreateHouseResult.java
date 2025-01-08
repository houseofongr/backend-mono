package com.hoo.aoo.admin.application.port.in;

import java.util.List;

public record CreateHouseResult(
    House house,
    List<Room> rooms
) {
    public record House(
            Long id,
            Long imageFileId,
            Long borderImageFileId,
            String title,
            String author,
            Integer roomCnt
    ) {
    }

    public record Room(
       Long id,
       Long imageFileId,
       String name
    ) {
    }
}
