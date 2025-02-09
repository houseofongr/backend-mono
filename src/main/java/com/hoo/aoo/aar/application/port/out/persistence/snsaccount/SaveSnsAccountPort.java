package com.hoo.aoo.aar.application.port.out.persistence.snsaccount;

import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;

public interface SaveSnsAccountPort {
    void save(SnsAccount snsAccount);
}
