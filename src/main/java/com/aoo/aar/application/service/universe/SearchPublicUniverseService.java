package com.aoo.aar.application.service.universe;

import com.aoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseUseCase;
import com.aoo.aar.application.port.out.persistence.universe.SearchPublicUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchPublicUniverseService implements SearchPublicUniverseUseCase {

    private final SearchPublicUniversePort searchPublicUniversePort;

    @Override
    public SearchPublicUniverseResult search(SearchPublicUniverseCommand command) {
        return searchPublicUniversePort.searchPublicUniverse(command);
    }
}
