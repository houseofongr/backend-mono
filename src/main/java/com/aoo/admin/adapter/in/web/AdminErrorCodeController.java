package com.aoo.admin.adapter.in.web;

import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.common.adapter.in.web.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminErrorCodeController {

    @GetMapping("/admin/error-codes")
    ResponseEntity<?> getError() {
        Map<String, ErrorResponse> responseMap = new HashMap<>();

        for (AdminErrorCode errorCode : AdminErrorCode.values())
            responseMap.put(errorCode.name(), ErrorResponse.of(errorCode));

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
