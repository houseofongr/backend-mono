package com.hoo.main.application.port.in.user;

public record CreateBusinessUserResult(
        String message,
        Long tempUserId,
        String email,
        String nickname,
        Boolean termsOfUseAgreement,
        Boolean personalInformationAgreement
) {
}
