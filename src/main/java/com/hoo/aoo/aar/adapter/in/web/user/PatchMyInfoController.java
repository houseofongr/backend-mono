package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchMyInfoController {

    @PatchMapping("/aar/users/me")
    public ResponseEntity<MessageDto> patchMyInfo() {
        return ResponseEntity.ok(new MessageDto("본인정보가 수정되었습니다."));
    }

}
