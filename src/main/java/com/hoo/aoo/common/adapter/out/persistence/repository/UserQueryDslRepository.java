package com.hoo.aoo.common.adapter.out.persistence.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.SearchUserCommand;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.data.domain.Page;

public interface UserQueryDslRepository {
    Page<UserJpaEntity> searchAll(QueryUserInfoCommand command);
    Page<UserJpaEntity> searchAll(SearchUserCommand command);
    Integer countHomeCountById(Long userId);
    Integer countActiveSoundSourceCountById(Long userId);

}
