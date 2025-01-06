package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.account.SnsAccount;

public interface SaveSnsAccountPort {
    void save(SnsAccount snsAccount);
}
