package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.HouseIdResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateHouseInfoController {

    @PostMapping("/admin/houses/update/{houseId}")
    ResponseEntity<HouseIdResult> update(@PathVariable Long houseId) {
        return new ResponseEntity<>(new HouseIdResult(houseId), HttpStatus.OK);
    }
}
