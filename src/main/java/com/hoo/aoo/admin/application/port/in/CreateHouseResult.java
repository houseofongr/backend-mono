package com.hoo.aoo.admin.application.port.in;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;

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
       Long imageFileId,
       String name
    ) {
        public static Room of(com.hoo.aoo.admin.domain.room.Room room) {
            return new Room(room.getImage().getImageId(), room.getId().getName());
        }
    }
}
