package com.hoo.aoo.admin.application.port.out.space;

import com.hoo.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.aoo.admin.domain.universe.space.Space;

public interface CreateSpacePort {
    Space createSpace(CreateSpaceCommand command, Space parent, Long imageId);
}
