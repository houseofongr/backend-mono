package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReadHouseController {

    @GetMapping("/admin/houses/{houseId}")
    public ResponseEntity<ReadHouseResult> getList(@PathVariable Long houseId) {

        ReadHouseResult result = new ReadHouseResult(
                new ReadHouseResult.House("cozy house", "leaf", "this is cozy house.", "January.15. 2024.", "January.15. 2025.", 5000F, 5000F, 1L),
                List.of(
                        new ReadHouseResult.Room("거실", 0F, 0F, 0F, 5000F, 1000F, 1L),
                        new ReadHouseResult.Room("주방", 0F, 1000F, 0F, 5000F, 1000F, 2L)
                )
        );

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
