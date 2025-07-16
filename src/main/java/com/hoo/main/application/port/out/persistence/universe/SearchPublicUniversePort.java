package com.hoo.main.application.port.out.persistence.universe;

import com.hoo.main.application.port.in.universe.SearchPublicUniverseCommand;
import com.hoo.main.application.port.in.universe.SearchPublicUniverseResult;

public interface SearchPublicUniversePort {
    SearchPublicUniverseResult searchPublicUniverse(SearchPublicUniverseCommand command);
}
