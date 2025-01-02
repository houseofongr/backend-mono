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
    ResponseEntity<?> regist() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
