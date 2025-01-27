package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteSoundSourceController {

    @DeleteMapping("/admin/sound-sources/{soundSourceId}")
    public ResponseEntity<MessageDto> deleteSoundSource(@PathVariable Long soundSourceId) {
        return ResponseEntity.ok(new MessageDto(soundSourceId + "번 음원이 삭제되었습니다."));
    }
}
