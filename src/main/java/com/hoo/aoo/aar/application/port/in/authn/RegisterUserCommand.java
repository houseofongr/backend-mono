package com.hoo.aoo.aar.application.port.in.authn;

public record RegisterUserCommand(
        Long snsId,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
