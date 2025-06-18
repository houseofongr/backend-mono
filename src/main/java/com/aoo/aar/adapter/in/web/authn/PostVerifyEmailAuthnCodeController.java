package com.aoo.aar.adapter.in.web.authn;

import com.aoo.aar.application.port.in.authn.VerifyEmailAuthnCodeUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostVerifyEmailAuthnCodeController {

    private final VerifyEmailAuthnCodeUseCase useCase;

    @PostMapping("/aar/authn/email-code/verify")
    ResponseEntity<MessageDto> sendMail(
            @RequestParam String email,
            @RequestParam String code) {
        return ResponseEntity.ok(useCase.verify(email, code));
    }
}
