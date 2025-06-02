package com.aoo.admin.application.port.out.universe;

import com.aoo.admin.domain.universe.Universe;

public interface SaveUniversePort {
    Long save(Universe universe);
}
