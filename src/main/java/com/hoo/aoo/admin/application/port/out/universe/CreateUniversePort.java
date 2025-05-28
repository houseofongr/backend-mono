package com.hoo.aoo.admin.application.port.out.universe;

import com.hoo.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.hoo.aoo.admin.domain.universe.Universe;

public interface CreateUniversePort {
    Universe createUniverse(CreateUniverseCommand command, Long thumbnailId, Long thumbMusicId, Long innerImageId);
}
