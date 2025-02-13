package com.hoo.aoo.admin.application.port.out.snsaccount;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;

public interface CreateSnsAccountPort {
    SnsAccount createSnsAccount(SnsDomain snsDomain, String snsId, String realName, String nickname, String email);
}
