package com.hoo.aoo.admin.adapter.in.web.house;

import com.hoo.aoo.admin.application.port.in.house.HouseIdResult;
import com.hoo.aoo.admin.application.port.in.house.CreateHouseUseCase;
import com.hoo.aoo.admin.application.service.house.Metadata;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostHouseController {

    private final Gson gson = new Gson();
    private final CreateHouseUseCase createHouseUseCase;

    @PostMapping("/admin/houses")
    public ResponseEntity<HouseIdResult> create(@RequestParam String metadata, HttpServletRequest request) {

        Map<String, MultipartFile> fileMap = new HashMap<>();

        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            fileMap = multiRequest.getFileMap();
        }

        Metadata metadata1 = gson.fromJson(metadata, Metadata.class);
        HouseIdResult result = createHouseUseCase.create(metadata1, fileMap);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
