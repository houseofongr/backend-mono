package com.aoo.admin.application.port.out.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceResult;
import com.aoo.admin.domain.universe.space.Space;

public interface UpdateSpacePort {
    UpdateSpaceResult.Detail update(Space space);
}
