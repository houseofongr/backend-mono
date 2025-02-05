package com.hoo.aoo.aar.domain.user;

import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class User {

    private final UserId userId;
    private final UserInfo userInfo;
    private final Agreement agreement;
    private final BaseTime baseTime;
    private final List<SnsAccount> snsAccounts;

    public User(UserId userId, UserInfo userInfo, Agreement agreement, BaseTime baseTime, List<SnsAccount> snsAccounts) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.agreement = agreement;
        this.baseTime = baseTime;
        this.snsAccounts = snsAccounts;
    }

    public static User register(Long id, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, SnsAccount snsAccount) {
        return new User(
                new UserId(id),
                new UserInfo(snsAccount.getSnsAccountInfo().getRealName(), snsAccount.getSnsAccountInfo().getNickname(), snsAccount.getSnsAccountInfo().getEmail()),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                null,
                List.of(snsAccount)
        );
    }

    public static User load(Long id, String realName, String nickname, String email, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<SnsAccount> snsAccounts) throws InvalidPhoneNumberException {
        return new User(
                new UserId(id),
                new UserInfo(realName, nickname, email),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                new BaseTime(createdTime, updatedTime),
                snsAccounts
        );
    }
}
