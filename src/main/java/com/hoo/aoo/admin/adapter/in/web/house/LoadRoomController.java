package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.LoadRoomResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoadRoomController {

    @GetMapping("/admin/houses/{houseId}/rooms/{roomName}")
    ResponseEntity<LoadRoomResult> load(
            @PathVariable Long houseId,
            @PathVariable String roomName
    ) {
        LoadRoomResult 거실 = new LoadRoomResult(new LoadRoomResult.Room("거실", 1000F, 1000F, 1L));
        return new ResponseEntity<>(거실, HttpStatus.OK);
    }

}
