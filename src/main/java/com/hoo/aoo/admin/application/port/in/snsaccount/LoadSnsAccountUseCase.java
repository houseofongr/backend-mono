package com.hoo.aoo.admin.application.port.in.snsaccount;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;

public interface LoadSnsAccountUseCase {
    SnsAccount loadSnsAccount(SnsDomain domain, String snsId);
}
