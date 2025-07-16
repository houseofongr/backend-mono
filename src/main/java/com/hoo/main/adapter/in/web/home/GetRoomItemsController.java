package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.QueryRoomItemsResult;
import com.hoo.main.application.port.in.home.QueryRoomItemsUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("AARGetRoomItemsController")
@RequiredArgsConstructor
public class GetRoomItemsController {

    private final QueryRoomItemsUseCase useCase;

    @GetMapping("/homes/rooms/items")
    public ResponseEntity<QueryRoomItemsResult> getRoomItems(
            @NotNull @Jwt("userId") Long userId,
            @RequestParam Long homeId,
            @RequestParam Long roomId
    ) {

        return ResponseEntity.ok(useCase.queryRoomItems(userId, homeId, roomId));
    }
}
