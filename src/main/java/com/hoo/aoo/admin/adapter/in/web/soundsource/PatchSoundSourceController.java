package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatchSoundSourceController {

    @PatchMapping("/admin/sound-sources/{soundSourceId}")
    ResponseEntity<MessageDto> updateSoundSource(@PathVariable Long soundSourceId) {
        return ResponseEntity.ok(new MessageDto(soundSourceId + "번 음원의 정보가 수정되었습니다."));
    }
}
