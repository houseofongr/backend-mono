package com.hoo.aoo.aar.adapter.in.web.home;

import com.hoo.aoo.aar.application.port.in.home.QueryItemSoundSourcesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetItemSoundSourcesController {

    @GetMapping("/aar/homes/items/sound-sources")
    public ResponseEntity<QueryItemSoundSourcesResult> getItemSoundSources() {
        return ResponseEntity.ok(new QueryItemSoundSourcesResult(
                "설이",
                List.of(
                        new QueryItemSoundSourcesResult.SoundSourceInfo(1L, "골골송", "2025년 설이가 보내는 골골송", "2025.02.02.", "2025.02.02."),
                        new QueryItemSoundSourcesResult.SoundSourceInfo(1L, "골골골송", "2026년 설이가 보내는 골골골송", "2026.02.02.", "2026.02.02.")
                )
        ));
    }
}
