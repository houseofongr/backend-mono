package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.application.port.in.home.QuerySoundSourceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AARGetSoundSourceController")
@RequiredArgsConstructor
public class GetSoundSourceController {

    @GetMapping("/aar/sound-sources")
    public ResponseEntity<QuerySoundSourceResult> getSoundSource() {
        return ResponseEntity.ok(
                new QuerySoundSourceResult("골골송", "2025년 설이가 보내는 골골송", "2025.02.02.", "2025.02.02.", 1L)
        );
    }
}
