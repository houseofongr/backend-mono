package com.hoo.aoo.admin.adapter.in.web.home;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteHomeController {

    @DeleteMapping("/admin/homes/{homeId}")
    public ResponseEntity<MessageDto> delete(@PathVariable Long homeId) {
        return new ResponseEntity<>(new MessageDto(homeId + "번 홈이 삭제되었습니다."), HttpStatus.OK);
    }

}
