package com.aoo.admin.application.port.out.snsaccount;

import com.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface SaveSnsAccountPort {
    void save(SnsAccount snsAccount);
}
