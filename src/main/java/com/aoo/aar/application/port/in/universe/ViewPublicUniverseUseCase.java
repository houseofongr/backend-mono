package com.aoo.aar.application.port.in.universe;

public interface ViewPublicUniverseUseCase {
    ViewPublicUniverseResult read(Long universeId, Long userId);
}
