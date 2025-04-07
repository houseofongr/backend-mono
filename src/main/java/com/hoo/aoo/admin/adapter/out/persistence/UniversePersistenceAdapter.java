package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.UniverseMapper;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.hoo.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.HashtagJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SaveUniversePort, FindUniversePort, UpdateUniversePort {

    private final HashtagJpaRepository hashtagJpaRepository;
    private final UniverseJpaRepository universeJpaRepository;
    private final UniverseMapper universeMapper;

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

        return universeJpaEntity.getId();
    }

    @Override
    public Optional<Universe> load(Long id) {
        Optional<UniverseJpaEntity> universeJpaEntity = universeJpaRepository.findById(id);
        return universeJpaEntity.map(universeMapper::mapToDomainEntity);
    }

    @Override
    public SearchUniverseResult search(SearchUniverseCommand command) {
        Page<SearchUniverseResult.UniverseInfo> entityPage = universeJpaRepository.searchAll(command).map(SearchUniverseResult.UniverseInfo::of);
        return new SearchUniverseResult(entityPage.getContent(), Pagination.of(entityPage));
    }

    @Override
    public void update(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow();

        List<UniverseHashtagJpaEntity> universeHashtags = targetEntity.getUniverseHashtags();
        Set<String> notExistTags = new HashSet<>(universe.getSocialInfo().getHashtags());
        for (int i = universeHashtags.size() - 1; i >= 0; i--) {
            boolean exist = false;
            for (String tag : universe.getSocialInfo().getHashtags()) {
                if (universeHashtags.get(i).getHashtag().getTag().equalsIgnoreCase(tag)) {
                    notExistTags.remove(tag);
                    exist = true;
                    break;
                }
            }
            if (!exist) universeHashtags.remove(i);
        }

        for (String tag : notExistTags) {
            universeHashtags.add(UniverseHashtagJpaEntity.create(targetEntity,getOrCreate(tag)));
        }

        targetEntity.update(universe);
    }
}
