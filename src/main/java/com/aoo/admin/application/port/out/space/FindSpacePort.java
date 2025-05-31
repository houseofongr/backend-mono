package com.aoo.admin.application.port.out.space;

import com.aoo.admin.domain.universe.space.Space;

import java.util.Optional;

public interface FindSpacePort {
    Optional<Space> loadSingle(Long id);
}
