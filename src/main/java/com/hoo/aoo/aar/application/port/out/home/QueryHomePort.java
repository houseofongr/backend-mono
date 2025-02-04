package com.hoo.aoo.aar.application.port.out.home;

import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;

public interface QueryHomePort {
    QueryUserHomesResult queryUserHome(Long userId);
}
