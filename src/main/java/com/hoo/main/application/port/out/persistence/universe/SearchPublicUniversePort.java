package com.hoo.main.application.port.out.persistence.universe;

import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseResult;

import java.util.List;

public interface SearchPublicUniversePort {
    SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command);

    List<Long> findNewPublicUniverseIdsLimit100Except(List<Long> exceptIds);
}
