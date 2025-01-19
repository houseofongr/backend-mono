package com.hoo.aoo.admin.adapter.in.web.user;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostHomeController {

    @PostMapping("/admin/homes")
    ResponseEntity<MessageDto> create(
            @RequestBody Request request
    ) {
        return new ResponseEntity<>(new MessageDto("0번 홈이 생성되었습니다."), HttpStatus.CREATED);
    }

    private record Request(
            Long userId,
            Long houseId
    ) {

    }
}
