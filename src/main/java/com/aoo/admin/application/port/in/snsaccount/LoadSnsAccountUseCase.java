package com.aoo.admin.application.port.in.snsaccount;

import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;

public interface LoadSnsAccountUseCase {
    SnsAccount loadSnsAccount(SnsDomain domain, String snsId);
}
