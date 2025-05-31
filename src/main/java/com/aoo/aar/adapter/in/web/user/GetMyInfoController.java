package com.aoo.aar.adapter.in.web.user;

import com.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.aoo.aar.application.port.in.user.QueryMyInfoUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyInfoController {

    private final QueryMyInfoUseCase useCase;

    @GetMapping("/aar/users/me")
    public ResponseEntity<QueryMyInfoResult> getMyInfo(@NotNull @Jwt("userId") Long userId) {
        return ResponseEntity.ok(useCase.queryMyInfo(userId));
    }
}
