package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoUseCase;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchRoomInfoController {

    private final UpdateRoomInfoUseCase updateRoomInfoUseCase;

    @PatchMapping("/admin/houses/rooms/{houseId}")
    ResponseEntity<MessageDto> update(
            @PathVariable Long houseId,
            @RequestBody Request request) {

        UpdateRoomInfoCommand command = new UpdateRoomInfoCommand(houseId, request.originalName, request.newName);
        return new ResponseEntity<>(updateRoomInfoUseCase.update(command), HttpStatus.OK);
    }

    private record Request(
            String originalName,
            String newName
    ) {

    }
}
