package com.hoo.aoo.admin.application.port.out.space;

import com.hoo.aoo.admin.domain.universe.space.Space;

import java.util.Optional;

public interface FindSpacePort {
    Optional<Space> loadSingle(Long id);
}
