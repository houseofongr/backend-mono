package com.hoo.aoo.admin.application.port.out.snsaccount;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;

public interface SaveSnsAccountPort {
    void save(SnsAccount snsAccount);
}
