package com.hoo.main.adapter.in.web.user;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.user.SearchMyInfoResult;
import com.hoo.main.application.port.in.user.QueryMyInfoUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMyInfoController {

    private final QueryMyInfoUseCase useCase;

    @GetMapping("/users/me")
    public ResponseEntity<SearchMyInfoResult> getMyInfo(@NotNull @Jwt("userId") Long userId) {
        return ResponseEntity.ok(useCase.queryMyInfo(userId));
    }
}
