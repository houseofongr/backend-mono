package com.hoo.aoo.admin.adapter.in.web.space;

import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PatchSpaceController {

    private final UpdateSpaceUseCase useCase;

    @PatchMapping("/admin/spaces/{spaceId}")
    public ResponseEntity<MessageDto> update(
            @PathVariable Long spaceId,
            @RequestBody UpdateSpaceCommand command) {

        return ResponseEntity.ok(useCase.update(spaceId, command));
    }
}
