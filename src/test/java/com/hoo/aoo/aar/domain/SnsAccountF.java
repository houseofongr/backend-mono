package com.hoo.aoo.aar.domain;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.account.SnsAccountId;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.PhoneNumber;
import com.hoo.aoo.common.enums.SnsDomain;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public enum SnsAccountF {

    REGISTERED_KAKAO("남상엽", "leaf", "leaf@example.com", "SNS_ID", SnsDomain.KAKAO, ZonedDateTime.now(), ZonedDateTime.now(), "010-0000-0000"),
    NOT_REGISTERED_KAKAO("남상엽", "leaf", "leaf@example.com", "SNS_ID", SnsDomain.KAKAO,ZonedDateTime.now(), ZonedDateTime.now(),  null),
    REGISTERED_KAKAO_2( "남엽돌", "spearoad", "spearoad@example.com", "SNS_ID_2", SnsDomain.KAKAO, ZonedDateTime.now(), ZonedDateTime.now(), "010-1234-5678"),
    NOT_REGISTERED_KAKAO_2( "남엽돌", "spearoad", "spearoad@example.com", "SNS_ID_2", SnsDomain.KAKAO, ZonedDateTime.now(), ZonedDateTime.now(), null);

    private final String name;
    private final String nickname;
    private final String email;
    private final String snsId;
    private final SnsDomain snsDomain;
    private final ZonedDateTime createdTime;
    private final ZonedDateTime updatedTime;
    private final String phoneNumber;

    public SnsAccount get() throws InvalidPhoneNumberException {

        return new UserMapper().mapToDomainEntity(
                email,
                new SnsAccountId(snsDomain, snsId),
                new Name(name, nickname),
                phoneNumber == null? null : new PhoneNumber(phoneNumber),
                new DateInfo(createdTime,updatedTime)
        );
    }
}
