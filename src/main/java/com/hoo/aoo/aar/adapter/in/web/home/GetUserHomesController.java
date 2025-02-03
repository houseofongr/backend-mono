package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AARGetUserHomesController")
@RequiredArgsConstructor
public class GetUserHomesController {

    @GetMapping("/aar/users/homes")
    public ResponseEntity<QueryUserHomesResult> getUserHomes(
            @Jwt("userId") Long userId
    ) {
        return ResponseEntity.ok(new QueryUserHomesResult(List.of(new QueryUserHomesResult.HomeInfo(1L, "leaf의 cozy house"), new QueryUserHomesResult.HomeInfo(2L, "leaf의 simple house"))));
    }
}
