package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.AdminUserMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.UserQueryDslRepository;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserPersistenceAdapter implements SearchUserPort {

    private final UserQueryDslRepository userQueryDslRepository;
    private final AdminUserMapper adminUserMapper;

    @Override
    public QueryUserInfoResult search(QueryUserInfoCommand command) {
        return adminUserMapper.mapToQueryResults(userQueryDslRepository.searchByCommand(command));
    }
}
