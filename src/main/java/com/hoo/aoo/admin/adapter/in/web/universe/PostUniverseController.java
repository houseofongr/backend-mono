package com.hoo.aoo.admin.adapter.in.web.universe;

import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseUseCase;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.common.application.port.in.MessageDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostUniverseController {

    private final CreateUniverseUseCase useCase;

    @PostMapping("/admin/universes")
    public ResponseEntity<MessageDto> create(@RequestParam String metadata, HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            return new ResponseEntity<>(
                    useCase.create(
                            gson.fromJson(metadata, CreateUniverseCommand.class),
                            multipartRequest.getFileMap()),
                    HttpStatus.CREATED);
        }

        throw new AdminException(AdminErrorCode.INVALID_REQUEST_TYPE);
    }
}
