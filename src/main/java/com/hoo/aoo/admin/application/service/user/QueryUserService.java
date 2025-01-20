package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryUserService implements QueryUserInfoUseCase {

    @Override
    public QueryUserInfoResult query(QueryUserInfoCommand command) {
        return null;
    }
}
