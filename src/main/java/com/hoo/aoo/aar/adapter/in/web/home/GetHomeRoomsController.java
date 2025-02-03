package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetHomeRoomsController {

    @GetMapping("/aar/users/homes/rooms")
    public ResponseEntity<QueryHomeRoomsResult> getHomeRooms() {
        return ResponseEntity.ok(
                new QueryHomeRoomsResult("leaf의 cozy house",
                        new QueryHomeRoomsResult.HouseInfo(5000f, 5000f, 1L),
                        List.of(
                                new QueryHomeRoomsResult.RoomInfo(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 2L),
                                new QueryHomeRoomsResult.RoomInfo(2L, "주방", 0f, 1000f, 0f, 5000f, 1000f, 3L))
                )
        );
    }
}
