package com.hoo.aoo.aar.adapter.in.web.authn.security.jwt;

import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.aar.application.service.AarException;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
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
public class JwtUtil {

    private final MACSigner signer;
    private final JwtAttribute jwtAttribute;
    private final SnsAccountJpaRepository snsAccountJpaRepository;

    public String getAccessToken(SnsAccount snsAccount) {
        SnsAccountJpaEntity snsAccountJpaEntity = snsAccountJpaRepository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId())
                .orElseThrow(() -> new AarException(AarErrorCode.SNS_ACCOUNT_NOT_FOUND));

        return getAccessToken(snsAccountJpaEntity);
    }

    public String getAccessToken(SnsAccountJpaEntity snsAccount) {

        if (snsAccount.getUserEntity() == null)
            return getAccessToken(snsAccount.getNickname(), -1L, snsAccount.getId(), Role.TEMP_USER);
        else
            return getAccessToken(snsAccount.getNickname(), snsAccount.getUserEntity().getId(), snsAccount.getId(), Role.USER);
    }

    private String getAccessToken(String nickname, Long userId, Long snsId, Role role) {
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
