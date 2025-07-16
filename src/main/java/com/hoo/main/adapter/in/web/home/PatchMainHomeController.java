package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.UpdateMainHomeUseCase;
import com.hoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchMainHomeController {

    private final UpdateMainHomeUseCase useCase;

    @PatchMapping("/homes/{homeId}/main")
    public ResponseEntity<MessageDto> patchHomeName(@Jwt("userId") Long userId,
                                                    @PathVariable Long homeId) {
        return ResponseEntity.ok(useCase.updateMainHome(userId, homeId));
    }
}
