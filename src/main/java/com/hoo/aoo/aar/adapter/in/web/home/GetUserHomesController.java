package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AARGetUserHomesController")
@RequiredArgsConstructor
public class GetUserHomesController {

    private final QueryUserHomesUseCase useCase;

    @GetMapping("/aar/homes")
    public ResponseEntity<QueryUserHomesResult> getUserHomes(
            @Jwt("userId") Long userId
    ) {
        return ResponseEntity.ok(useCase.queryUserHomes(userId));
    }
}
