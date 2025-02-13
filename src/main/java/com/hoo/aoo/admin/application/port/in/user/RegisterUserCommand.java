package com.hoo.aoo.admin.application.port.in.user;

public record RegisterUserCommand(
        Long snsId,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
