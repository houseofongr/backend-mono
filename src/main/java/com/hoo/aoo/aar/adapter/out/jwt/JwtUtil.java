package com.hoo.aoo.aar.adapter.out.jwt;

import com.hoo.aoo.aar.adapter.in.web.authn.security.JwtAttribute;
import com.hoo.aoo.aar.application.port.out.jwt.IssueAccessTokenPort;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.domain.Role;
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
public class JwtUtil implements IssueAccessTokenPort {

    private final MACSigner signer;
    private final JwtAttribute jwtAttribute;

    @Override
    public String issueAccessToken(SnsAccount snsAccount) {

        Long userId = (snsAccount.getUserId() != null && snsAccount.getUserId().getId() != null) ? snsAccount.getUserId().getId() : -1L;
        Role role = (snsAccount.getUserId() != null && snsAccount.getUserId().getId() != null) ? Role.USER : Role.TEMP_USER;

        return issueAccessToken(snsAccount.getSnsAccountInfo().getNickname(), userId, snsAccount.getSnsAccountId().getPersistenceId(), role);
    }

    private String issueAccessToken(String nickname, Long userId, Long snsId, Role role) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(nickname)
                    .issuer(jwtAttribute.issuer())
                    .claim("userId", userId)
                    .claim("snsId", snsId)
                    .claim("role", role)
                    .expirationTime(new Date(System.currentTimeMillis() + jwtAttribute.expire()))
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
