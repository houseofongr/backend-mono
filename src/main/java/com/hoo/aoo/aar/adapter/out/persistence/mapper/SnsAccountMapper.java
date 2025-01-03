package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.adapter.in.web.authn.security.dto.OAuth2Dto;
import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.common.enums.SnsDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SnsAccountMapper {

    default SnsAccountJpaEntity kakaoUserToSnsAccount(OAuth2Dto.KakaoUserInfo kakaoUser) {
        return SnsAccountJpaEntity.regist(
                kakaoUser.kakao_account().profile().nickname(),
                kakaoUser.kakao_account().email(),
                kakaoUser.id(),
                SnsDomain.KAKAO);
    }
}
