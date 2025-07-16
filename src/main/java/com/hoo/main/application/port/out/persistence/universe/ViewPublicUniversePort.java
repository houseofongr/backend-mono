package com.hoo.main.application.port.out.persistence.universe;

import com.hoo.admin.domain.universe.TraversalComponents;

public interface ViewPublicUniversePort {
    TraversalComponents viewPublicUniverse(Long universeId);
}
