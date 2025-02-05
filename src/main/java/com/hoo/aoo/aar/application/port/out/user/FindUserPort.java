package com.hoo.aoo.aar.application.port.out.user;

import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.domain.user.User;

import java.util.Optional;

public interface FindUserPort {
    QueryMyInfoResult queryMyInfo(Long userId);
    Optional<User> load(Long id);
}
