package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SnsAccountMapper {

    public SnsAccount mapToDomainEntity(SnsAccountJpaEntity snsAccountJpaEntity) {
        return SnsAccount.load(
                snsAccountJpaEntity.getId(),
                snsAccountJpaEntity.getSnsDomain(),
                snsAccountJpaEntity.getSnsId(),
                snsAccountJpaEntity.getRealName(),
                snsAccountJpaEntity.getNickname(),
                snsAccountJpaEntity.getEmail(),
                snsAccountJpaEntity.getCreatedTime(),
                snsAccountJpaEntity.getUpdatedTime(),
                snsAccountJpaEntity.getUserEntity() == null ? null : snsAccountJpaEntity.getUserEntity().getId()
        );
    }
}
