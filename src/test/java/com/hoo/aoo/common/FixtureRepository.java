package com.hoo.aoo.common;

import com.hoo.aoo.admin.application.service.Metadata;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FixtureRepository {

    public static Room getRoom(HouseId houseId, String name) throws AxisLimitExceededException, AreaLimitExceededException {
        return Room.create(houseId, name, 0f, 0f, 0f, 1f, 1f, 1L);
    }

    public static House getHouseWithRoom(HouseId houseId) throws AxisLimitExceededException, AreaLimitExceededException, HouseRelationshipException, RoomDuplicatedException {
        return House.create(houseId, 5000f, 5000f, List.of(getRoom(houseId,"거실"), getRoom(houseId,"주방")));
    }

    public static Metadata getMetadata() {

        return new Gson().fromJson(getMetadataJson(), Metadata.class);
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

    public static Metadata getUpdateMetadata() {
        String data = """
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
                      "originalName": "거실",
                      "newName": "거실2",
                      "x": 123,
                      "y": 456,
                      "z": 1,
                      "width": 100,
                      "height": 200
                    },
                    {
                      "form": "room2",
                      "originalName": "주방",
                      "newName": "주방2",
                      "x": 234,
                      "y": 345,
                      "z": 2,
                      "width": 200,
                      "height": 200
                    }
                  ]
                }
                """;
        return new Gson().fromJson(data, Metadata.class);
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
}
