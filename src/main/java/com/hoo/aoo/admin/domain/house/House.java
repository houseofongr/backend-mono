package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class House {

    private final HouseId id;
    private final Author author;
    private final HouseArea area;
    private final HouseImages images;

    public House(HouseArea area, HouseId id, Author author, HouseImages images) {
        this.area = area;
        this.id = id;
        this.author = author;
        this.images = images;
    }
}
