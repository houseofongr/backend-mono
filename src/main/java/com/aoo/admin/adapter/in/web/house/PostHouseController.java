package com.aoo.admin.adapter.in.web.house;

import com.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.aoo.admin.application.port.in.house.CreateHouseResult;
import com.aoo.admin.application.port.in.house.CreateHouseUseCase;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import static com.aoo.common.util.GsonUtil.gson;

@RestController
@RequiredArgsConstructor
public class PostHouseController {

    private final CreateHouseUseCase createHouseUseCase;

    @PostMapping("/admin/houses")
    public ResponseEntity<CreateHouseResult> create(@RequestParam String metadata, HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest multipartRequest) {

            return new ResponseEntity<>(
                    createHouseUseCase.create(gson.fromJson(metadata, CreateHouseMetadata.class), multipartRequest.getFileMap()),
                    HttpStatus.CREATED);

        } else throw new AdminException(AdminErrorCode.INVALID_REQUEST_TYPE);
    }
}
