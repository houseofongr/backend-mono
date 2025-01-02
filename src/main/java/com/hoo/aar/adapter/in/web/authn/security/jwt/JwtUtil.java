package com.hoo.aar.adapter.in.web.authn.security.jwt;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.common.enums.Role;
import com.hoo.aar.domain.SnsAccount;
import com.hoo.aar.domain.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final MACSigner signer;
    private final JwtAttribute jwtAttribute;

    public String getAccessToken(SnsAccountJpaEntity snsAccount) {
        if (snsAccount.getUserEntity() == null)
            return getAccessToken(snsAccount.getName(),-1L, snsAccount.getId(), Role.TEMP_USER);
        return getAccessToken(snsAccount.getName(),snsAccount.getUserEntity().getId(), snsAccount.getId(), Role.USER);
    }

    public String getAccessToken(SnsAccount snsAccount) {
        return getAccessToken(snsAccount.getUser().getName(), snsAccount.getUser().getId(), snsAccount.getId(), Role.USER);
    }

    private String getAccessToken(String name, Long userId, Long snsId, Role role) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(name)
                    .issuer(jwtAttribute.issuer())
                    .claim("userId", userId)
                    .claim("snsId", snsId)
                    .claim("role", role)
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
