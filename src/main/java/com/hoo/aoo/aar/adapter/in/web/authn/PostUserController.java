package com.hoo.aoo.aar.adapter.in.web.authn;

import com.hoo.aoo.aar.adapter.in.web.authn.security.Jwt;
import com.hoo.aoo.aar.application.port.in.authn.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.authn.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostUserController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/aar/authn/regist")
    ResponseEntity<?> regist(@RequestBody Request request,
                             @Jwt("snsId") Long snsId) {

        RegisterUserCommand command = new RegisterUserCommand(
                snsId,
                request.termsOfUseAgreement,
                request.personalInformationAgreement);

        return new ResponseEntity<>(registerUserUseCase.register(command), HttpStatus.CREATED);
    }

    private record Request(
            Boolean termsOfUseAgreement,
            Boolean personalInformationAgreement
    ) {
    }
}
