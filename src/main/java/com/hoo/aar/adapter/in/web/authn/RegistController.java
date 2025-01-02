package com.hoo.aar.adapter.in.web.authn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aar/authn/regist")
public class RegistController {

    @PostMapping
    ResponseEntity<?> regist(RegistApiDto.Request dto) {
        RegistApiDto.Response response = new RegistApiDto.Response("test", "token");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
