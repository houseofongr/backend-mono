package com.hoo.aoo.aar.application.port.out.database.snsaccount;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;

public interface CreateSnsAccountPort {
    SnsAccount createSnsAccount(SnsDomain snsDomain, String snsId, String realName, String nickname, String email);
}
