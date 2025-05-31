package com.aoo.admin.adapter.in.web.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostUpdateUniverseController {

    private final UpdateUniverseUseCase useCase;

    @PostMapping("/admin/universes/thumbnail/{universeId}")
    public ResponseEntity<MessageDto> updateThumbnail(
            @PathVariable Long universeId,
            @RequestPart(value = "thumbnail") MultipartFile thumbnail) {

        return ResponseEntity.ok(useCase.updateThumbnail(universeId, thumbnail));

    }

    @PostMapping("/admin/universes/thumb-music/{universeId}")
    public ResponseEntity<MessageDto> updateThumbMusic(
            @PathVariable Long universeId,
            @RequestPart(value = "thumbMusic") MultipartFile thumbMusic) {

        return ResponseEntity.ok(useCase.updateThumbMusic(universeId, thumbMusic));

    }

    @PostMapping("/admin/universes/inner-image/{universeId}")
    public ResponseEntity<MessageDto> updateInnerImage(
            @PathVariable Long universeId,
            @RequestPart(value = "innerImage") MultipartFile innerImage) {

        return ResponseEntity.ok(useCase.updateInnerImage(universeId, innerImage));

    }

}
