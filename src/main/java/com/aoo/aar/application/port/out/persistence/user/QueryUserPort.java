package com.aoo.aar.application.port.out.persistence.user;

import com.aoo.aar.application.port.in.user.QueryMyInfoResult;

public interface QueryUserPort {
    QueryMyInfoResult queryMyInfo(Long userId);
}
