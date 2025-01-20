package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserPersistenceAdapter implements SearchUserPort {

    @Override
    public QueryUserInfoResult search(QueryUserInfoCommand command) {
        return null;
    }
}
