package com.aoo.admin.application.port.out.space;

import com.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.aoo.admin.domain.universe.space.Space;

public interface CreateSpacePort {
    Space createSpace(CreateSpaceCommand command, Long innerImageFileId);
}
