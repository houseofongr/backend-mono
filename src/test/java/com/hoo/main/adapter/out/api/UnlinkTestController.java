package com.hoo.main.adapter.out.api;

import com.hoo.main.application.port.in.authn.OAuth2Dto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UnlinkTestController {

    @PostMapping(value = "/public/unlink", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<OAuth2Dto.KakaoUnlinkResponse> unlink(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestParam Map<String, String> map) {

        if (!authorizationHeader.contains("KakaoAK") || !map.get("target_id_type").equals("user_id") || !map.get("target_id").equals("SNS_ID"))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(new OAuth2Dto.KakaoUnlinkResponse(1L));
    }
}
