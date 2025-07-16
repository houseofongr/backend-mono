package com.hoo.main.adapter.in.web.authn.mockcontroller;

import com.hoo.main.application.port.in.authn.SNSLoginResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginDocumentationOAuth2ControllerV2 {

    @GetMapping("/authn/login/kakao/v2")
    public ResponseEntity<SNSLoginResult> kakaoLogin() {
        return new ResponseEntity<>(new SNSLoginResult(
                "leaf",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
                "kakao",
                false), HttpStatus.OK);
    }
}
