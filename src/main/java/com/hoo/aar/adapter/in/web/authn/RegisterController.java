package com.hoo.aar.adapter.in.web.authn;

import com.hoo.aar.adapter.in.web.SnsId;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aar.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/aar/authn/regist")
    ResponseEntity<?> regist(RegisterUserRequest request, @SnsId Long snsId) {

        RegisterUserCommand.In command = new RegisterUserCommand.In(
                snsId,
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
