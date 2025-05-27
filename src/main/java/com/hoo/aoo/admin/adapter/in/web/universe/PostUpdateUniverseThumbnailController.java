package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostUpdateUniverseThumbnailController {

    private final UpdateUniverseUseCase useCase;

    @PostMapping("/admin/universes/thumbnail/{universeId}")
    public ResponseEntity<MessageDto> update(
            @PathVariable Long universeId,
            @RequestPart(value = "thumbnail") MultipartFile thumbnail) {

        return ResponseEntity.ok(useCase.updateThumbnail(universeId, thumbnail));

    }

}
