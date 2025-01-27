package com.hoo.aoo.admin.adapter.in.web.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.CreateSoundSourceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostSoundSourceController {

    @PostMapping("/admin/items/{itemId}/sound-sources")
    public ResponseEntity<CreateSoundSourceResult> createSoundSource(@PathVariable Long itemId) {
        return new ResponseEntity<>(new CreateSoundSourceResult(1L), HttpStatus.CREATED);
    }
}
