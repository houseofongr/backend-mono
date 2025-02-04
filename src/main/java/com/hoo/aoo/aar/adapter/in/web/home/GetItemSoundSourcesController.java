package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.QueryItemSoundSourceUseCase;
import com.hoo.aoo.aar.application.port.in.home.QueryItemSoundSourcesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetItemSoundSourcesController {

    private final QueryItemSoundSourceUseCase useCase;

    @GetMapping("/aar/homes/items/sound-sources")
    public ResponseEntity<QueryItemSoundSourcesResult> getItemSoundSources(
            @Jwt("userId") Long userId,
            @RequestParam Long itemId
    ) {
        return ResponseEntity.ok(useCase.queryItemSoundSources(userId, itemId));
    }
}
