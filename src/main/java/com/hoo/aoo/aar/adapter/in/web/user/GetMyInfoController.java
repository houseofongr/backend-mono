package com.hoo.aoo.aar.adapter.in.web.user;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyInfoController {

    private final QueryMyInfoUseCase useCase;

    @GetMapping("/aar/users/me")
    public ResponseEntity<QueryMyInfoResult> getMyInfo(@Jwt("userId") Long userId) {
        return ResponseEntity.ok(useCase.queryMyInfo(userId));
    }
}
