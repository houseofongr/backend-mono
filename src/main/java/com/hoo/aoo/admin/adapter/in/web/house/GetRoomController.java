package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.QueryRoomCommand;
import com.hoo.aoo.admin.application.port.in.house.QueryRoomInfoUseCase;
import com.hoo.aoo.admin.application.port.in.house.QueryRoomResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetRoomController {

    private final QueryRoomInfoUseCase queryRoomInfoUseCase;

    @GetMapping("/admin/houses/{houseId}/rooms/{roomId}")
    ResponseEntity<QueryRoomResult> load(
            @PathVariable Long houseId,
            @PathVariable String roomId
    ) {
        QueryRoomCommand command = new QueryRoomCommand(houseId,roomId);
        return new ResponseEntity<>(queryRoomInfoUseCase.query(command), HttpStatus.OK);
    }

}
