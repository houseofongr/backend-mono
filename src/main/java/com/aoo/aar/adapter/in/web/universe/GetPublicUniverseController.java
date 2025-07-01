package com.aoo.aar.adapter.in.web.universe;

import com.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.aoo.aar.application.port.in.universe.ViewPublicUniverseResult;
import com.aoo.aar.application.port.in.universe.ViewPublicUniverseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPublicUniverseController {

    private final ViewPublicUniverseUseCase useCase;

    @GetMapping("/aar/universes/{universeId}")
    ResponseEntity<ViewPublicUniverseResult> read(
            @PathVariable Long universeId,
            @Jwt("userId") Long userId
    ) {
        return ResponseEntity.ok(useCase.read(universeId, userId));
    }
}
