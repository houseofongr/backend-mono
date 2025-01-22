package com.hoo.aoo.common;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.admin.domain.user.User;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureRepository {

    private static final Gson gson = new Gson();

    public static Room getRoom(HouseId houseId, String name) throws Exception {
        return Room.create(houseId, name, 0f, 0f, 0f, 1f, 1f);
    }

    public static Room getRoom(String roomName) throws Exception {
        return getRoom(getHouseId(),roomName);
    }

    public static House getHouseWithRoom() throws Exception {
        return getHouseWithRoom(getHouseId());
    }

    public static HouseId getHouseId() {
        return new HouseId("cozy house", "leaf", "this is cozy house");
    }

    public static House getHouseWithRoom(HouseId houseId) throws Exception {
        return House.create(houseId, 5000f, 5000f, List.of(getRoom(houseId,"거실"), getRoom(houseId,"주방")));
    }

    public static House getHouse(HouseId houseId, List<Room> rooms) throws Exception {
        return House.create(houseId, 5000f, 5000f, rooms);
    }


    public static User getUser() {
        return User.load("leaf","남상엽");
    }

    public static Home getHome() throws Exception {
        return Home.create(getHouseWithRoom(),getUser());
    }

    public static CreateHouseMetadata getCreateHouseMetadata() {
        return new CreateHouseMetadata(
                new CreateHouseMetadata.HouseData("cozy house", "leaf", "this is cozy house.", "house", "border", 5000f, 5000f),
                List.of(
                        new CreateHouseMetadata.RoomData("room1", "거실", 123f, 456f, 1f, 100f, 200f),
                        new CreateHouseMetadata.RoomData("room2", "주방", 234f, 345f, 2f, 200f, 200f)
                )
        );
    }

    public static CreateItemMetadata getCreateItemMetadata() {
        return new CreateItemMetadata(
                List.of(
                        new CreateItemMetadata.ItemData("record1","강아지", ItemType.CIRCLE, new CreateItemMetadata.CircleData(10.5f,200f,200f),null,null),
                        new CreateItemMetadata.ItemData("record2","설이", ItemType.RECTANGLE, null, new CreateItemMetadata.RectangleData(100f,100f,10f,10f,5f), null),
                        new CreateItemMetadata.ItemData("record3","화분", ItemType.ELLIPSE, null, null, new CreateItemMetadata.EllipseData(500f,500f,15f,15f,90f))
                        )
                );
    }

    public static String getCreateHouseMetadataJson() {
        return gson.toJson(getCreateHouseMetadata());
    }

    public static String getCreateItemMetadataJson() {
        return gson.toJson(getCreateItemMetadata());
    }
}
