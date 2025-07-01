package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.common.adapter.out.persistence.entity.TraversalJpaEntityComponents;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.domain.Page;

public interface UniverseQueryDslRepository {
    Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command);

    Page<UniverseJpaEntity>  searchAllPublic(SearchPublicUniverseCommand command);

    TraversalJpaEntityComponents findAllTreeComponentById(Long universeId);

    TraversalJpaEntityComponents findAllPublicTreeComponentById(Long universeId);

    boolean checkIsLiked(Long universeId, Long userId);

}
