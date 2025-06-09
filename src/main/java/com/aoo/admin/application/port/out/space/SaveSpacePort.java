package com.aoo.admin.application.port.out.space;

import com.aoo.admin.application.port.in.space.CreateSpaceResult;
import com.aoo.admin.domain.universe.space.Space;

public interface SaveSpacePort {
    CreateSpaceResult save(Space space);
}
