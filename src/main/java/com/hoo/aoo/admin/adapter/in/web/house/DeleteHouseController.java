package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteHouseController {

    @DeleteMapping("/admin/houses/{houseId}")
    ResponseEntity<MessageDto> delete(
            @PathVariable Long houseId
    ) {
        return new ResponseEntity<>(new MessageDto(houseId + "번 하우스가 삭제되었습니다."), HttpStatus.OK);
    }

}
