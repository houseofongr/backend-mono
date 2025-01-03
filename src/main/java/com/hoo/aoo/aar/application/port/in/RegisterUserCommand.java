package com.hoo.aoo.aar.application.port.in;

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
