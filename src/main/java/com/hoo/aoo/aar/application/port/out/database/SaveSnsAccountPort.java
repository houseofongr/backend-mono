package com.hoo.aoo.aar.application.port.out.database;

import com.hoo.aoo.aar.domain.SnsAccount;

public interface SaveSnsAccountPort {
    SnsAccount save(SnsAccount snsAccount);
}
