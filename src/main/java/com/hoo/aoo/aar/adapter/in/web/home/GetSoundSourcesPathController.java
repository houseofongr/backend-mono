package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.home.QuerySoundSourcesPathCommand;
import com.hoo.aoo.aar.application.port.in.home.QuerySoundSourcesPathResult;
import com.hoo.aoo.aar.application.port.in.home.QuerySoundSourcesPathUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSoundSourcesPathController {

    private final QuerySoundSourcesPathUseCase useCase;

    @GetMapping("/aar/sound-sources/path")
    public ResponseEntity<QuerySoundSourcesPathResult> getSoundSourcesPath(
            @Jwt("userId") Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(useCase.querySoundSourcesPath(new QuerySoundSourcesPathCommand(userId,pageable)));
    }
}
