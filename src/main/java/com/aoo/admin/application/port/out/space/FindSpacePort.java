package com.aoo.admin.application.port.out.space;

import com.aoo.admin.domain.universe.space.Space;

public interface FindSpacePort {
    Space find(Long id);
    Long findUniverseId(Long id);
}
