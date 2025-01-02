package com.hoo.aar.application.port.in;

import com.hoo.aar.domain.User;

public record RegisterUserCommand() {
    public record In(
            Long snsId,
            Boolean recordAgreement,
            Boolean personalInformationAgreement
    ) {

    }
    public record Out(
            String nickname,
            String accessToken
    ) {

    }
}
