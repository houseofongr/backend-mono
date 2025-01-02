package com.hoo.aar.adapter.in.web.authn;

public record RegistApiDto(

) {
    public record Request(
            String nickName,
            Boolean recordAgreement,
            Boolean personalInformationAgreement
    ) {

    }

    public record Response(
            String username,
            String accessToken
    ) {

    }
}
