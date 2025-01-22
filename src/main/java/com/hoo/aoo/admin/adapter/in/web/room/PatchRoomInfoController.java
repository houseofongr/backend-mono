package com.hoo.aoo.admin.adapter.in.web.room;

import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoUseCase;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatchRoomInfoController {

    private final UpdateRoomInfoUseCase updateRoomInfoUseCase;

    @PatchMapping("/admin/houses/rooms")
    ResponseEntity<MessageDto> update(@RequestBody List<UpdateRoomInfoCommand.RoomInfo> requests) {

        UpdateRoomInfoCommand command = new UpdateRoomInfoCommand(requests);

        return new ResponseEntity<>(updateRoomInfoUseCase.update(command), HttpStatus.OK);
    }

}
