package com.hoo.aoo.admin.adapter.in.web.house;

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
public class UpdateRoomInfoController {

    @PatchMapping("/admin/houses/rooms/{houseId}")
    ResponseEntity<MessageDto> update(
            @PathVariable Long houseId,
            @RequestBody Request request) {
        return new ResponseEntity<>(new MessageDto(houseId + "번 하우스 " + request.originalName + " 룸의 정보 수정이 완료되었습니다."), HttpStatus.OK);
    }

    private record Request(
            String originalName,
            String newName
    ) {

    }
}
