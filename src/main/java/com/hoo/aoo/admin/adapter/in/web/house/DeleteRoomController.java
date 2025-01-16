package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.QueryRoomCommand;
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

    @DeleteMapping("/admin/houses/{houseId}/rooms/{roomName}")
    ResponseEntity<MessageDto> delete(
            @PathVariable Long houseId,
            @PathVariable String roomName
    ) {
        QueryRoomCommand command = new QueryRoomCommand(houseId, roomName);
        return new ResponseEntity<>(new MessageDto(houseId + "번 하우스 " + roomName + " 룸이 삭제되었습니다."), HttpStatus.OK);
    }
}
