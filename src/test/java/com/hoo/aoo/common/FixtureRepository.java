package com.hoo.aoo.common;

import com.hoo.aoo.admin.application.service.house.CreateHouseMetadata;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.user.User;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureRepository {

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

    public static CreateHouseMetadata getMetadata() {
        return new Gson().fromJson(getMetadataJson(), CreateHouseMetadata.class);
    }

    public static User getUser() {
        return User.load("leaf","남상엽");
    }

    public static String getMetadataJson() {
        return  """
                {
                  "house": {
                    "title": "cozy house",
                    "author": "leaf",
                    "description": "this is cozy house.",
                    "width": 5000,
                    "height": 5000,
                    "houseForm": "house",
                    "borderForm": "border"
                  },
                  "rooms": [
                    {
                      "form": "room1",
                      "name": "거실",
                      "x": 123,
                      "y": 456,
                      "z": 1,
                      "width": 100,
                      "height": 200
                    },
                    {
                      "form": "room2",
                      "name": "주방",
                      "x": 234,
                      "y": 345,
                      "z": 2,
                      "width": 200,
                      "height": 200
                    }
                  ]
                }
                """;
    }

    public static Map<String, MultipartFile> getFileMap() {
        MockMultipartFile house = new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes());
        MockMultipartFile border = new MockMultipartFile("border", "border.png", "image/png", "border file".getBytes());
        MockMultipartFile room1 = new MockMultipartFile("room1", "livingRoom.png", "image/png", "livingRoom file".getBytes());
        MockMultipartFile room2 = new MockMultipartFile("room2", "kitchen.png", "image/png", "kitchen file".getBytes());

        Map<String, MultipartFile> map = new HashMap<>();

        map.put("house", house);
        map.put("border", border);
        map.put("room1", room1);
        map.put("room2", room2);

        return map;
    }

    public static Home getHome() throws Exception {
        return Home.create(getHouseWithRoom(),getUser());
    }
}
