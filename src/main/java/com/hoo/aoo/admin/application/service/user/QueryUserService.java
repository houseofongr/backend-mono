package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.*;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserService implements QueryUserInfoUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    @Transactional(readOnly = true)
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        return searchUserPort.search(command);
    }

}
