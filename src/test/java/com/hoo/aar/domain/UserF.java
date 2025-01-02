package com.hoo.aar.domain;

import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.adapter.out.persistence.mapper.UserMapperImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserF {
    NOT_REGISTERED(SnsAccountF.KAKAO_NOT_REGISTERED.get(), true, true);

    private final SnsAccount snsAccount;
    private final Boolean recordAgreement;
    private final Boolean personalInformationAgreement;

    public User get() {
        return User.regist(snsAccount, recordAgreement, personalInformationAgreement);
    }
}
