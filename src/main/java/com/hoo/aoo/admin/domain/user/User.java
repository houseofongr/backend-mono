package com.hoo.aoo.admin.domain.user;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
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

    private User(UserId userId, UserInfo userInfo, Agreement agreement, BaseTime baseTime, List<SnsAccount> snsAccounts) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.agreement = agreement;
        this.baseTime = baseTime;
        this.snsAccounts = snsAccounts;
    }

    public static User load(Long id, String realName, String nickname, String email, Boolean termsOfUseAgreement, Boolean personalInformationAgreement, ZonedDateTime createdTime, ZonedDateTime updatedTime, List<SnsAccount> snsAccounts) {
        return new User(
                new UserId(id),
                new UserInfo(realName, nickname, email),
                new Agreement(termsOfUseAgreement, personalInformationAgreement),
                new BaseTime(createdTime, updatedTime),
                snsAccounts
        );
    }

    public void updateNickname(String nickname) {
        this.userInfo.updateNickname(nickname);
    }

    public UserInfo getDeletedUserInfo() {
        return new UserInfo(
                userInfo.getMaskedRealName(),
                userInfo.getMaskedNickname(),
                userInfo.getMaskedEmail());
    }
}
