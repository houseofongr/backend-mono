package com.hoo.aoo.aar.application.port.out.jwt;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface IssueAccessTokenPort {
    String issueAccessToken(SnsAccount snsAccount);
}
