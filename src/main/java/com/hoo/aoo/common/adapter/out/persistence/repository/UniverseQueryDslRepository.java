package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.domain.Page;

public interface UniverseQueryDslRepository {
    Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command);
}
