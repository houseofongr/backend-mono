package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHomeRoomsController {

    private final QueryHomeRoomsUseCase useCase;

    @GetMapping("/aar/homes/rooms")
    public ResponseEntity<QueryHomeRoomsResult> getHomeRooms(
            @Jwt("userId") Long userId,
            @RequestParam Long homeId
    ) {
        return ResponseEntity.ok(useCase.queryHomeRooms(userId, homeId));
    }
}
