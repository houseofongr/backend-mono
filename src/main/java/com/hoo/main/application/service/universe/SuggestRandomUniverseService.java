package com.hoo.main.application.service.universe;

import com.hoo.admin.domain.universe.Universe;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseCommand;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseResult;
import com.hoo.main.application.port.in.universe.SuggestRandomUniverseUseCase;
import com.hoo.main.application.port.out.persistence.universe.LoadUniversePort;
import com.hoo.main.application.port.out.persistence.universe.SearchPublicUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SuggestRandomUniverseService implements SuggestRandomUniverseUseCase {

    private final SearchPublicUniversePort searchPublicUniversePort;
    private final LoadUniversePort loadUniversePort;

    @Override
    public SuggestRandomUniverseResult suggestRandomSidebarUniverse(SuggestRandomUniverseCommand command) {

        List<Long> universeIds = searchPublicUniversePort.findNewPublicUniverseIdsLimit100Except(command.exceptIds());

        Collections.shuffle(universeIds);
        List<Long> randomFourIds = universeIds.subList(0, Math.min(command.size(), universeIds.size()));

        List<Universe> universes = new ArrayList<>(loadUniversePort.loadAllUniverseOnly(randomFourIds));
        Collections.shuffle(universes);

        return new SuggestRandomUniverseResult(
                universes.stream().map(SuggestRandomUniverseResult.UniverseSidebarInfo::from).toList()
        );
    }
}
