package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.Getter;

import java.util.List;

@Getter
public class House {

    private final HouseId id;
    private final Author author;
    private final Area area;
    private final HouseImages images;
    private final List<Room> rooms;

    private House(HouseId id, Area area, Author author, HouseImages images, List<Room> rooms) {
        this.area = area;
        this.id = id;
        this.author = author;
        this.images = images;
        this.rooms = rooms;
    }

    public static House createNewHouse(HouseId houseId, Author author, Area area, HouseImages houseImages, List<Room> rooms) {
        return new House(houseId,area,author,houseImages, rooms);
    }
}
