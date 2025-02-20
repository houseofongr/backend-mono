package com.hoo.aoo.file.adapter.out.jwt;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.common.domain.Role;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.application.port.out.jwt.VerifyAccessTokenPort;
import com.hoo.aoo.file.application.service.FileErrorCode;
import com.hoo.aoo.file.application.service.FileException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccessTokenAdapter implements VerifyAccessTokenPort {

    private final JwtDecoder jwtDecoder;
    private final FileJpaRepository fileJpaRepository;

    @Override
    public void verifyImageToken(String accessToken) {
        Role role = Role.parse((String) jwtDecoder.decode(accessToken).getClaims().get("role"));
        assert role.getAuthorities().contains(Authority.ALL_PRIVATE_IMAGE_ACCESS);
    }

    @Override
    public boolean verifyAudioToken(String accessToken, Long fileId) {

        Map<String, Object> claims = jwtDecoder.decode(accessToken).getClaims();

        assert Role.parse((String) claims.get("role")).getAuthorities().contains(Authority.MY_PRIVATE_AUDIO_ACCESS);

        Long userId = (Long) claims.get("userId");
        FileJpaEntity file = fileJpaRepository.findById(fileId).orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

        return file.getOwner().getId().equals(userId);
    }
}
