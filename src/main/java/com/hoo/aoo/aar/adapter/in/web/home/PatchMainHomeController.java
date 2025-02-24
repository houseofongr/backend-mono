package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.UpdateMainHomeUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchMainHomeController {

    private final UpdateMainHomeUseCase useCase;

    @PatchMapping("/aar/homes/{homeId}/main")
    public ResponseEntity<MessageDto> patchHomeName(@Jwt("userId") Long userId,
                                                    @PathVariable Long homeId) {
        return ResponseEntity.ok(useCase.updateMainHome(userId, homeId));
    }
}
