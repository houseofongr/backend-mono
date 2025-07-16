package com.hoo.main.adapter.in.web.home;

import com.hoo.main.adapter.in.web.authn.security.Jwt;
import com.hoo.main.application.port.in.home.QuerySoundSourcesPathCommand;
import com.hoo.main.application.port.in.home.QuerySoundSourcesPathResult;
import com.hoo.main.application.port.in.home.QuerySoundSourcesPathUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSoundSourcesPathController {

    private final QuerySoundSourcesPathUseCase useCase;

    @GetMapping("/sound-sources/path")
    public ResponseEntity<QuerySoundSourcesPathResult> getSoundSourcesPath(
            @NotNull @Jwt("userId") Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(useCase.querySoundSourcesPath(new QuerySoundSourcesPathCommand(userId, pageable)));
    }
}
