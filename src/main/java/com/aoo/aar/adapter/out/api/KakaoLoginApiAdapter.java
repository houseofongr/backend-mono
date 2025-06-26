package com.aoo.aar.adapter.out.api;

import com.aoo.common.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.aoo.aar.application.port.out.api.UnlinkKakaoLoginPort;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;
import com.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoLoginApiAdapter implements UnlinkKakaoLoginPort {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final KakaoLoginApi kakaoLoginApi;

    @Override
    public void unlink(Long userId) {
        for (SnsAccountJpaEntity entity : snsAccountJpaRepository.findAllByUserId(userId)) {
            if (entity.getSnsDomain().equals(SnsDomain.KAKAO)) {
                kakaoLoginApi.unlink(entity.getSnsId());
                return;
            }
        }
    }
}
