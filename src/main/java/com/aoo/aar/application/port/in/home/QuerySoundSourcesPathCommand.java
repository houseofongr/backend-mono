package com.aoo.aar.application.port.in.home;

import org.springframework.data.domain.Pageable;

public record QuerySoundSourcesPathCommand(
        Long userId,
        Pageable pageable
) {
}
