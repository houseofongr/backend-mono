package com.hoo.main.adapter.in.web.authn;

import com.hoo.main.application.port.in.authn.LoginBusinessUserResult;
import com.hoo.main.application.port.in.authn.LoginBusinessUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLoginBusinessUserController {

    private final LoginBusinessUserUseCase useCase;

    @PostMapping("/authn/business/login")
    ResponseEntity<LoginBusinessUserResult> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(useCase.login(email, password));
    }
}
