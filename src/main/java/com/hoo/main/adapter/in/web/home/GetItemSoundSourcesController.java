package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.QueryItemSoundSourceUseCase;
import com.hoo.main.application.port.in.home.QueryItemSoundSourcesResult;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetItemSoundSourcesController {

    private final QueryItemSoundSourceUseCase useCase;

    @GetMapping("/homes/items/sound-sources")
    public ResponseEntity<QueryItemSoundSourcesResult> getItemSoundSources(
            @NotNull @Jwt("userId") Long userId,
            @RequestParam Long itemId
    ) {
        return ResponseEntity.ok(useCase.queryItemSoundSources(userId, itemId));
    }
}
