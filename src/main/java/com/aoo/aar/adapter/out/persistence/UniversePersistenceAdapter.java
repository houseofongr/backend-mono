package com.aoo.aar.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.mapper.UniverseMapper;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.aoo.aar.application.port.out.persistence.universe.SearchPublicUniversePort;
import com.aoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.aoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component("AARUniversePersistenceAdapter")
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SearchPublicUniversePort {

    private final UniverseMapper universeMapper;
    private final UniverseJpaRepository universeJpaRepository;

    @Override
    public SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command) {
        Page<SearchPublicUniverseResult.UniverseListInfo> entityPage = universeJpaRepository.searchAllPublic(command)
                .map(universeJpaEntity -> universeMapper.mapToSearchPublicUniverseListInfo(command.userId(), universeJpaEntity));

        return new SearchPublicUniverseResult(entityPage.getContent(), Pagination.of(entityPage));
    }
}
