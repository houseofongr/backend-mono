package com.hoo.aoo.admin.application.port.out.home;

import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;

import java.util.Optional;

public interface FindHomePort {
    boolean exist(Long homeId);

    Optional<QueryHomeResult> findHome(Long id);

    QueryUserHomesResult findUserHomes(Long userId);
}
