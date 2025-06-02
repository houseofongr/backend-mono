package com.aoo.admin.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.aoo.admin.adapter.out.persistence.mapper.UniverseMapper;
import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.port.in.universe.SearchUniverseResult;
import com.aoo.admin.application.port.out.universe.DeleteUniversePort;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.application.port.out.universe.SaveUniversePort;
import com.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.adapter.out.persistence.entity.HashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseHashtagJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.HashtagJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SaveUniversePort, FindUniversePort, UpdateUniversePort, DeleteUniversePort {

    private final HashtagJpaRepository hashtagJpaRepository;
    private final UniverseJpaRepository universeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UniverseMapper universeMapper;

    //TODO : 여러 태그 한번에 조회하도록 로직 수정(현재 쿼리가 tag 개수만큼 나감)
    public HashtagJpaEntity getOrCreate(String tag) {
        return hashtagJpaRepository.findByTag(tag)
                .orElseGet(() -> hashtagJpaRepository.save(HashtagJpaEntity.create(tag)));
    }

    @Override
    public Long save(Universe universe) {
        UserJpaEntity author = userJpaRepository.findById(universe.getBasicInfo().getAuthorId()).orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

        UniverseJpaEntity universeJpaEntity = UniverseJpaEntity.create(universe, author);

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
    public SearchUniverseResult.UniverseDetailInfo find(Long id) {
        return SearchUniverseResult.UniverseDetailInfo.of(universeJpaRepository.findById(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND)));
    }

    @Override
    public void update(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));
        targetEntity.update(universe);

        // Author Update
        if (!Objects.equals(targetEntity.getAuthor().getId(), universe.getBasicInfo().getAuthorId())) {
            UserJpaEntity userJpaEntity = userJpaRepository.findById(universe.getBasicInfo().getAuthorId()).orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));
            targetEntity.updateAuthor(userJpaEntity);
        }

        // Hashtag Update
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
            universeHashtags.add(UniverseHashtagJpaEntity.create(targetEntity, getOrCreate(tag)));
        }
    }

    @Override
    public void delete(Universe universe) {
        UniverseJpaEntity targetEntity = universeJpaRepository.findById(universe.getId()).orElseThrow();
        universeJpaRepository.delete(targetEntity);
    }
}
