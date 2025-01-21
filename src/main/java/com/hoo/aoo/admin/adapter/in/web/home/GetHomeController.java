package com.hoo.aoo.admin.adapter.in.web.home;

import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetHomeController {

    @GetMapping("/admin/homes/{homeId}")
    public ResponseEntity<QueryHomeResult> getHome(@PathVariable Long homeId) {
        QueryHomeResult 거실 = new QueryHomeResult(
                1L, "January.21. 2025", "January.21. 2025",
                new QueryHomeResult.HouseInfo(5000f, 5000f, 1L),
                List.of(new QueryHomeResult.RoomInfo(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 1L))
        );

        return new ResponseEntity<>(거실, HttpStatus.OK);
    }

}
