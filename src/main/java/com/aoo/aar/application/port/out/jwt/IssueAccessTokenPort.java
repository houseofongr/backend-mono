package com.aoo.aar.application.port.out.jwt;

import com.aoo.aar.application.port.out.persistence.user.BusinessUserInfo;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface IssueAccessTokenPort {
    String issueAccessToken(SnsAccount snsAccount);
    String issueAccessToken(BusinessUserInfo businessUserInfo);
}
