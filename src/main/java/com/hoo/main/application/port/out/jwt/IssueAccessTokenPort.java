package com.hoo.main.application.port.out.jwt;

import com.hoo.main.application.port.out.persistence.user.BusinessUserInfo;
import com.hoo.admin.domain.user.snsaccount.SnsAccount;

public interface IssueAccessTokenPort {
    String issueAccessToken(SnsAccount snsAccount);
    String issueAccessToken(BusinessUserInfo businessUserInfo);
}
