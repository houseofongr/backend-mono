package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.application.port.in.home.QueryRoomItemsResult;
import com.hoo.aoo.admin.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AARGetRoomItemsController")
@RequiredArgsConstructor
public class GetRoomItemsController {

    @GetMapping("/aar/homes/rooms/items")
    public ResponseEntity<QueryRoomItemsResult> getRoomItems() {
        return ResponseEntity.ok(
                new QueryRoomItemsResult(
                        new QueryRoomItemsResult.RoomData("거실", 5000f, 1000f, 2L),
                        List.of(
                                new QueryRoomItemsResult.ItemData(1L, "설이", ItemType.RECTANGLE,
                                        null, new QueryRoomItemsResult.RectangleData(100f, 100f, 15f, 15f, 5f), null),
                                new QueryRoomItemsResult.ItemData(2L, "강아지", ItemType.CIRCLE,
                                        new QueryRoomItemsResult.CircleData(200f, 200f, 10.5f), null, null),
                                new QueryRoomItemsResult.ItemData(3L, "화분", ItemType.ELLIPSE,
                                        null, null, new QueryRoomItemsResult.EllipseData(500f, 500f, 15f, 10f, 90f))
                        )
                )
        );
    }
}
