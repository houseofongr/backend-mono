package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
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

    public static House create(String title, String author, Integer width, Integer height, Long basicImageId, Long borderImageId, List<Room> rooms) throws AreaLimitExceededException {

        HouseId houseId = new HouseId(title);
        Author author_ = new Author(author);
        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);

        return new House(houseId, area, author_, houseImages, rooms);
    }
}
