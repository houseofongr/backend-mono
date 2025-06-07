package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.adapter.out.persistence.mapper.SpaceMapper;
import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.application.port.in.space.UpdateSpaceResult;
import com.aoo.admin.application.port.out.space.FindSpacePort;
import com.aoo.admin.application.port.out.space.SaveSpacePort;
import com.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.adapter.out.persistence.entity.SpaceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.SpaceJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
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
    public CreateSpaceResult save(Long universeId, Space space) {
        UniverseJpaEntity universeJpaEntity = universeJpaRepository.findById(universeId).orElseThrow();
        SpaceJpaEntity spaceJpaEntity = space.isRoot()?
                SpaceJpaEntity.create(space, universeJpaEntity) :
                SpaceJpaEntity.createChild(space, spaceJpaRepository.findById(space.getBasicInfo().getParentSpaceId()).orElseThrow());

        spaceJpaRepository.save(spaceJpaEntity);

        return CreateSpaceResult.of(spaceJpaEntity);
    }

    @Override
    public UpdateSpaceResult.Detail update(Space space) {
        SpaceJpaEntity spaceJpaEntity = spaceJpaRepository.findById(space.getId()).orElseThrow();
        spaceJpaEntity.update(space);

        return UpdateSpaceResult.Detail.of(spaceJpaEntity);
    }
}
