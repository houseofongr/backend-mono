package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSoundSourceController {

    @GetMapping("/admin/sound-sources/{soundSourceId}")
    public ResponseEntity<QuerySoundSourceResult> query(@PathVariable Long soundSourceId) {
        return ResponseEntity.ok(new QuerySoundSourceResult("골골송", "2025년 설이가 보내는 골골송", "2025.01.27.", "2025.01.27.", true, 1L));
    }
}
