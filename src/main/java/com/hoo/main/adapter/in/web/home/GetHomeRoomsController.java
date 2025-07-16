package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.main.application.port.in.home.QueryHomeRoomsUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetHomeRoomsController {

    private final QueryHomeRoomsUseCase useCase;

    @GetMapping("/homes/rooms")
    public ResponseEntity<QueryHomeRoomsResult> getHomeRooms(
            @NotNull @Jwt("userId") Long userId,
            @RequestParam Long homeId
    ) {
        return ResponseEntity.ok(useCase.queryHomeRooms(userId, homeId));
    }
}
