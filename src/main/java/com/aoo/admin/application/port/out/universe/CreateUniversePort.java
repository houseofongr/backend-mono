package com.aoo.admin.application.port.out.universe;

import com.aoo.admin.application.port.in.universe.CreateUniverseCommand;
import com.aoo.admin.domain.universe.Universe;

public interface CreateUniversePort {
    Universe createUniverse(CreateUniverseCommand command, Long thumbMusicId, Long thumbnailId, Long innerImageId);
}
