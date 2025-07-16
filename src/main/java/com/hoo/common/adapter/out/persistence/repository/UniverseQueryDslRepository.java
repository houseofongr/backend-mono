package com.hoo.common.adapter.out.persistence.repository;

import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.common.adapter.out.persistence.entity.TraversalJpaEntityComponents;
import com.hoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UniverseQueryDslRepository {
    Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command);

    Page<UniverseJpaEntity>  searchAllPublic(SearchPublicUniverseCommand command);

    TraversalJpaEntityComponents findAllTreeComponentById(Long universeId);

    TraversalJpaEntityComponents findAllPublicTreeComponentById(Long universeId);

    boolean checkIsLiked(Long universeId, Long userId);

    List<Long> findNewPublicUniverseIdLimit100Except(List<Long> exceptIds);
}
