package com.hoo.aoo.aar.application.port.out.jwt;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;

public interface IssueAccessTokenPort {
    String issueAccessToken(SnsAccount snsAccount);
}
