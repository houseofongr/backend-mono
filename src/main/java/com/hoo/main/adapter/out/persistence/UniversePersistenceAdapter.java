package com.hoo.main.adapter.out.persistence;

import com.hoo.admin.domain.universe.Universe;
import com.hoo.common.adapter.out.persistence.entity.UniverseJpaEntity;
import com.hoo.main.adapter.out.persistence.mapper.UniverseMapper;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseCommand;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseResult;
import com.hoo.main.application.port.out.persistence.universe.CheckIsLikedUniversePort;
import com.hoo.main.application.port.out.persistence.universe.LoadUniversePort;
import com.hoo.main.application.port.out.persistence.universe.SearchPublicUniversePort;
import com.hoo.main.application.port.out.persistence.universe.ViewPublicUniversePort;
import com.hoo.main.application.service.AarErrorCode;
import com.hoo.main.application.service.AarException;
import com.hoo.admin.domain.universe.TraversalComponents;
import com.hoo.common.adapter.out.persistence.repository.UniverseJpaRepository;
import com.hoo.common.application.port.in.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AARUniversePersistenceAdapter")
@RequiredArgsConstructor
public class UniversePersistenceAdapter implements SearchPublicUniversePort, CheckIsLikedUniversePort, ViewPublicUniversePort, LoadUniversePort {

    private final UniverseMapper universeMapper;
    private final UniverseJpaRepository universeJpaRepository;

    @Override
    public SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command) {
        Page<SearchPublicUniverseResult.UniverseListInfo> entityPage = universeJpaRepository.searchAllPublic(command)
                .map(universeJpaEntity -> universeMapper.mapToSearchPublicUniverseListInfo(command.userId(), universeJpaEntity));

        return new SearchPublicUniverseResult(entityPage.getContent(), Pagination.of(entityPage));
    }

    @Override
    public List<Long> findNewPublicUniverseIdsLimit100Except(List<Long> exceptIds) {
        return universeJpaRepository.findNewPublicUniverseIdLimit100Except(exceptIds);
    }

    @Override
    public boolean checkIsLiked(Long universeId, Long userId) {
        return universeJpaRepository.checkIsLiked(universeId, userId);
    }

    @Override
    public TraversalComponents viewPublicUniverse(Long universeId) {
        universeJpaRepository.findById(universeId).orElseThrow(() -> new AarException(AarErrorCode.UNIVERSE_NOT_FOUND)).view();

        return universeMapper.mapToTraversalComponents(universeJpaRepository.findAllPublicTreeComponentById(universeId));
    }

    @Override
    public List<Universe> loadAllUniverseOnly(List<Long> universeIds) {
        return universeJpaRepository.findAllById(universeIds).stream().map(universeMapper::mapToDomainEntity).toList();
    }
}
