package com.aoo.common.adapter.out.persistence.entity;

import java.util.List;

public record TraversalJpaEntityComponents(
        UniverseJpaEntity universeJpaEntity,
        List<SpaceJpaEntity> spaceJpaEntities,
        List<ElementJpaEntity> elementJpaEntities
) {

}
