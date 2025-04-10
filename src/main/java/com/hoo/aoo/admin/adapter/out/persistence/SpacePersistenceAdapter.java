package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.hoo.aoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.aoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpacePersistenceAdapter implements FindSpacePort, SaveSpacePort, UpdateSpacePort {

    private final UniverseJpaRepository universeJpaRepository;
    private final SpaceJpaRepository spaceJpaRepository;
    private final SpaceMapper spaceMapper;

    @Override
    public Optional<Space> loadSingle(Long id) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(id).orElseThrow();
        return Optional.of(spaceMapper.mapToSingleEntity(spaceJpaEntity));
    }

    @Override
    public Long save(Space space, Long universeId) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(universeId).orElseThrow();
        SpaceJpaEntity spaceJpaEntity = space.isRoot()?
                SpaceJpaEntity.create(space, universeJpaEntity) :
                SpaceJpaEntity.createChild(space, spaceJpaRepository.findById(space.getTreeInfo().getParentSpace().getId()).orElseThrow());

        spaceJpaRepository.save(spaceJpaEntity);

        return spaceJpaEntity.getId();
    }

    @Override
    public void update(Space space) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(space.getId()).orElseThrow();
        spaceJpaEntity.update(space);
    }
}
