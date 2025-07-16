package com.hoo.main.application.port.out.persistence.user;

import com.hoo.main.application.port.in.user.SearchMyInfoResult;

public interface SearchUserPort {
    SearchMyInfoResult queryMyInfo(Long userId);
    boolean existUserByNickname(String nickname);
}
