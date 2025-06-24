package com.aoo.aar.application.port.out.persistence.user;

import com.aoo.aar.application.port.in.user.SearchMyInfoResult;

public interface SearchUserPort {
    SearchMyInfoResult queryMyInfo(Long userId);
    boolean existUserByNickname(String nickname);
}
