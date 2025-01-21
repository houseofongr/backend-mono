package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import org.springframework.data.domain.Page;

public interface UserQueryDslRepository {
    Page<UserJpaEntity> searchByCommand(QueryUserInfoCommand command);
}
