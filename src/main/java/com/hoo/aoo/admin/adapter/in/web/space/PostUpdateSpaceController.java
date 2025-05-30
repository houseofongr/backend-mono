package com.hoo.aoo.admin.adapter.in.web.space;

import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
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
public class PostUpdateSpaceController {

    private final UpdateSpaceUseCase useCase;

    @PostMapping("/admin/spaces/inner-image/{spaceId}")
    public ResponseEntity<MessageDto> updateInnerImage(
            @PathVariable Long spaceId,
            @RequestPart(value = "innerImage") MultipartFile innerImage) {

        return ResponseEntity.ok(useCase.updateInnerImage(spaceId, innerImage));

    }
}
