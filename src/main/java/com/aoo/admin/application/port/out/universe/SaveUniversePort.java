package com.aoo.admin.application.port.out.universe;

import com.aoo.admin.application.port.in.universe.CreateUniverseResult;
import com.aoo.admin.domain.universe.Universe;

public interface SaveUniversePort {
    CreateUniverseResult save(Universe universe);
}
