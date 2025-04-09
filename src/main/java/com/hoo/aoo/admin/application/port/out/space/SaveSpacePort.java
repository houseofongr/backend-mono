package com.hoo.aoo.admin.application.port.out.space;

import com.hoo.aoo.admin.domain.universe.space.Space;

public interface SaveSpacePort {
    Long save(Space space, Long universeId);
}
