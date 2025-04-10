package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseUseCase;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.common.application.port.in.MessageDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostUpdateUniverseController {

    private final UpdateUniverseUseCase useCase;

    @PostMapping("/admin/universes/update")
    public ResponseEntity<MessageDto> update(@RequestParam String metadata,
                                             @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                             @RequestPart(value = "thumbMusic", required = false) MultipartFile thumbMusic) {

        UpdateUniverseCommand baseCommand = gson.fromJson(metadata, UpdateUniverseCommand.class);
        UpdateUniverseCommand fullCommand = UpdateUniverseCommand.from(baseCommand, thumbnail, thumbMusic);

        return ResponseEntity.ok(useCase.update(fullCommand));
    }

}
