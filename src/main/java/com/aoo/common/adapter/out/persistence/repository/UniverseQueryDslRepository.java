package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.common.adapter.out.persistence.entity.TraversalJpaEntityComponents;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.domain.Page;

public interface UniverseQueryDslRepository {
    Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command);

    TraversalJpaEntityComponents findAllTreeComponentById(Long universeId);
}
