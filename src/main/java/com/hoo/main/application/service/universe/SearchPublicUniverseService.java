package com.hoo.main.application.service.universe;

import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseResult;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseUseCase;
import com.hoo.main.application.port.out.persistence.universe.SearchPublicUniversePort;
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
