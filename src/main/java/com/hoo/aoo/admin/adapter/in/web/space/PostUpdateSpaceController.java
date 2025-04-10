package com.hoo.aoo.admin.adapter.in.web.space;

import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.util.GsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostUpdateSpaceController {

    private final UpdateSpaceUseCase useCase;

    @PostMapping("/admin/spaces/update")
    public ResponseEntity<MessageDto> update(@RequestParam String metadata, @RequestPart(name = "image", required = false) MultipartFile image) {
        UpdateSpaceCommand baseCommand = gson.fromJson(metadata, UpdateSpaceCommand.class);
        UpdateSpaceCommand fullCommand = UpdateSpaceCommand.from(baseCommand,image);

        return ResponseEntity.ok(useCase.update(fullCommand));
    }
}
