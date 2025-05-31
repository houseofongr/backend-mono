package com.aoo.admin.adapter.in.web.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
