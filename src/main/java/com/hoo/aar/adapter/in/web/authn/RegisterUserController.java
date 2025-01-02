package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.adapter.in.web.authn.config.Jwt;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aar.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterUserController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/aar/authn/regist")
    ResponseEntity<?> regist(@RequestBody RegisterUserRequest request, @Jwt("snsId") String snsId) {

        RegisterUserCommand.In command = new RegisterUserCommand.In(
                Long.parseLong(snsId),
                request.recordAgreement,
                request.personalInformationAgreement);

        return new ResponseEntity<>(registerUserUseCase.register(command), HttpStatus.OK);
    }

    record RegisterUserRequest(
            Boolean recordAgreement,
            Boolean personalInformationAgreement
    ) {
    }
}
