package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoUseCase;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryUserService implements QueryUserInfoUseCase {

    private final SearchUserPort searchUserPort;

    @Override
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        return searchUserPort.search(command);
    }
}
