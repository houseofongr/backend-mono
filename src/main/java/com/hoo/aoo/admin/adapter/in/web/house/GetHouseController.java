package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHouseController {

    private final QueryHouseUseCase queryHouseUseCase;

    @GetMapping("/admin/houses/{houseId}")
    public ResponseEntity<QueryHouseResult> getList(@PathVariable Long houseId) {
        return new ResponseEntity<>(queryHouseUseCase.query(houseId), HttpStatus.OK);
    }

}
