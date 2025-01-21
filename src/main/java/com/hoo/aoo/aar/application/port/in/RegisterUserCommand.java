package com.hoo.aoo.aar.application.port.in;

public record RegisterUserCommand(
        Long snsId,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
