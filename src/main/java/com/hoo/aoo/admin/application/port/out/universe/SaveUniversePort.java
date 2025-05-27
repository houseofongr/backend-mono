package com.hoo.aoo.admin.application.port.out.universe;

import com.hoo.aoo.admin.domain.universe.Universe;

public interface SaveUniversePort {
    Long save(Universe universe, Long authorId);
}
