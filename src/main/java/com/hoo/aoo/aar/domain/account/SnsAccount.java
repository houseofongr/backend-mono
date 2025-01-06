package com.hoo.aoo.aar.domain.account;

import com.hoo.aoo.aar.domain.DateInfo;
import com.hoo.aoo.aar.domain.Name;
import com.hoo.aoo.aar.domain.user.PhoneNumber;
import com.hoo.aoo.common.enums.SnsDomain;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SnsAccount {
    private final SnsAccountId snsAccountId;
    private final Name name;
    private final String email;
    private final PhoneNumber userId;
    private final DateInfo dateInfo;

    public SnsAccount(String email, SnsAccountId snsAccountId, Name name, PhoneNumber userId, DateInfo dateInfo) {
        this.email = email;
        this.snsAccountId = snsAccountId;
        this.name = name;
        this.userId = userId;
        this.dateInfo = dateInfo;
    }
}
