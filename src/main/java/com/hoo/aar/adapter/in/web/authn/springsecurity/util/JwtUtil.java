package com.hoo.aar.adapter.in.web.authn.springsecurity.util;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.common.Role;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final MACSigner signer;
    private final JwtAttribute jwtAttribute;

    public String getAccessToken(SnsAccountJpaEntity snsAccount) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(snsAccount.getName())
                    .issuer(jwtAttribute.issuer())
                    .claim("userId", snsAccount.getUserJpaEntity() != null? snsAccount.getUserJpaEntity() : -1)
                    .claim("snsId", snsAccount.getSnsId())
                    .claim("role", Role.TEMP_USER)
                    .expirationTime(new Date(new Date().getTime() + jwtAttribute.expire()))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (JOSEException e) {
            log.error("JWT Sign Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
