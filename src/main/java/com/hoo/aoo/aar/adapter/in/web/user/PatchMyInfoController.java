package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.admin.application.port.in.user.UpdateUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.UpdateUserInfoUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchMyInfoController {

    private final UpdateUserInfoUseCase useCase;

    @PatchMapping("/aar/users/me")
    public ResponseEntity<MessageDto> patchMyInfo(
            @Jwt("userId") Long userId,
            @RequestBody UpdateUserInfoCommand command
    ) {
        return ResponseEntity.ok(useCase.updateUserInfo(userId, command));
    }

}
