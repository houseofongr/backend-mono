package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.QuerySoundSourceResult;
import com.hoo.main.application.port.in.home.QuerySoundSourceUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("AARGetSoundSourceController")
@RequiredArgsConstructor
public class GetSoundSourceController {

    private final QuerySoundSourceUseCase useCase;

    @GetMapping("/homes/sound-sources")
    public ResponseEntity<QuerySoundSourceResult> getSoundSource(
            @NotNull @Jwt("userId") Long userId,
            @RequestParam Long soundSourceId
    ) {
        return ResponseEntity.ok(useCase.querySoundSource(userId, soundSourceId));
    }
}
