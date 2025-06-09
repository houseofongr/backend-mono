package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.aoo.admin.application.port.in.user.SearchUserCommand;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.domain.Page;

public interface UserQueryDslRepository {
    Page<UserJpaEntity> searchAll(QueryUserInfoCommand command);

    Page<UserJpaEntity> searchAll(SearchUserCommand command);

    Integer countHomeCountById(Long userId);

    Integer countActiveSoundSourceCountById(Long userId);

}
