package com.hoo.main.application.service.user;

import com.hoo.main.application.port.in.user.SearchUserNicknameResult;
import com.hoo.main.application.port.in.user.SearchUserNicknameUseCase;
import com.hoo.main.application.port.out.persistence.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchUserNicknameService implements SearchUserNicknameUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    public SearchUserNicknameResult search(String nickname) {
        return new SearchUserNicknameResult(searchUserPort.existUserByNickname(nickname));
    }
}
