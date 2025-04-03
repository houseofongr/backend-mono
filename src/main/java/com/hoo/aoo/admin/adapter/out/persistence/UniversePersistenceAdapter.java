package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.HashtagJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SaveUniversePort {

    private final HashtagJpaRepository hashtagJpaRepository;
    private final UniverseJpaRepository universeJpaRepository;

    public HashtagJpaEntity getOrCreate(String tag) {
        return hashtagJpaRepository.findByTag(tag)
                .orElseGet(() -> hashtagJpaRepository.save(HashtagJpaEntity.create(tag)));
    }

    @Override
    public Long save(Universe universe) {
        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.create(universe);

        for (String tag : universe.getSocialInfo().getHashtags())
            universeJpaEntity.getUniverseHashtags().add(UniverseHashtagJpaEntity.create(universeJpaEntity, getOrCreate(tag)));

        universeJpaRepository.save(universeJpaEntity);

        return universe.getId();
    }
}
