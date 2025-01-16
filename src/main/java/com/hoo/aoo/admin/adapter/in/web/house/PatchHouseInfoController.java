package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoUseCase;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatchHouseInfoController {

    private final UpdateHouseInfoUseCase updateHouseInfoUseCase;

    @PatchMapping("/admin/houses/{houseId}")
    ResponseEntity<MessageDto> update(
            @PathVariable Long houseId,
            @RequestBody Request request) {

        UpdateHouseInfoCommand command = new UpdateHouseInfoCommand(houseId,
                request.title(),
                request.author(),
                request.description());

        return new ResponseEntity<>(updateHouseInfoUseCase.update(command), HttpStatus.OK);
    }

    private record Request(
            String title,
            String author,
            String description
    ) {

    }
}
