package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.ReadHouseResult;
import com.hoo.aoo.admin.application.port.in.LoadHouseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoadHouseController {

    private final LoadHouseUseCase loadHouseUseCase;

    @GetMapping("/admin/houses/{houseId}")
    public ResponseEntity<ReadHouseResult> getList(@PathVariable Long houseId) {
        return new ResponseEntity<>(loadHouseUseCase.get(houseId), HttpStatus.OK);
    }

}
