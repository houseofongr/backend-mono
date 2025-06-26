package com.aoo.aar.application.port.out.persistence.universe;

import com.aoo.aar.application.port.in.universe.SearchPublicUniverseCommand;
import com.aoo.aar.application.port.in.universe.SearchPublicUniverseResult;

public interface SearchPublicUniversePort {
    SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command);
}
