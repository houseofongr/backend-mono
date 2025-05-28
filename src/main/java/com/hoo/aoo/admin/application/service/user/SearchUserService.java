package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.aoo.admin.application.port.in.user.SearchUserResult;
import com.hoo.aoo.admin.application.port.in.user.SearchUserUseCase;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchUserService implements SearchUserUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    @Transactional(readOnly = true)
    public SearchUserResult query(SearchUserCommand command) {
        return searchUserPort.search(command);
    }

}
