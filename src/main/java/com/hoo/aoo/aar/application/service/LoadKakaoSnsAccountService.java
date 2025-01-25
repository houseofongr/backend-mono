package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.SNSLoginResponse;
import com.hoo.aoo.aar.adapter.in.web.authn.security.dto.OAuth2Dto;
import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.hoo.aoo.common.util.GsonUtil.gson;

@Component
@AllArgsConstructor
public class LoadKakaoSnsAccountService implements LoadSnsAccountService {

    private final SnsAccountJpaRepository snsAccountJpaRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public OAuth2User load(OAuth2User user) {

        OAuth2Dto.KakaoUserInfo userInfo = gson.fromJson(gson.toJsonTree(user.getAttributes()), OAuth2Dto.KakaoUserInfo.class);

        SnsAccountJpaEntity snsAccountInDB = snsAccountJpaRepository.findWithUserEntity(SnsDomain.KAKAO, userInfo.id())
                .orElseGet(() -> save(userInfo));

        SNSLoginResponse response = snsAccountInDB.getUserEntity() == null ?
                SNSLoginResponse.of(snsAccountInDB, jwtUtil.getAccessToken(snsAccountInDB), true) :
                SNSLoginResponse.of(snsAccountInDB, jwtUtil.getAccessToken(snsAccountInDB), false);

        return new DefaultOAuth2User(user.getAuthorities(), response.getAttributes(), "nickname");
    }

    private SnsAccountJpaEntity save(OAuth2Dto.KakaoUserInfo userInfo) {

        String name = userInfo.kakao_account().profile().nickname();
        String email = userInfo.kakao_account().email();
        String snsId = userInfo.id();

        SnsAccountJpaEntity newSnsAccount = new SnsAccountJpaEntity(null, name, name, email, snsId, SnsDomain.KAKAO, null);
        snsAccountJpaRepository.save(newSnsAccount);

        return newSnsAccount;
    }
}
