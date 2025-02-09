package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteMyAccountController {

    @DeleteMapping("/aar/users")
    public ResponseEntity<MessageDto> deleteMyAccount() {
        return ResponseEntity.ok(new MessageDto("회원탈퇴가 완료되었습니다."));
    }
}
