package com.hoo.main.application.port.out.persistence.universe;

import com.hoo.admin.domain.universe.Universe;

import java.util.List;

public interface LoadUniversePort {
    List<Universe> loadAllUniverseOnly(List<Long> universeIds);
}
