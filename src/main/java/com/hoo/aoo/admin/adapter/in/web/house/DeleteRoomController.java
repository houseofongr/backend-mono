package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.DeleteRoomUseCase;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteRoomController {

    private final DeleteRoomUseCase deleteRoomUseCase;

    @DeleteMapping("/admin/houses/{houseId}/rooms/{roomName}")
    ResponseEntity<MessageDto> delete(
            @PathVariable Long houseId,
            @PathVariable String roomName
    ) {
        return new ResponseEntity<>(deleteRoomUseCase.delete(houseId, roomName), HttpStatus.OK);
    }
}
