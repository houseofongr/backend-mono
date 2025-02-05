package com.hoo.aoo.aar.application.port.in.home;

import org.springframework.data.domain.Pageable;

public record QuerySoundSourcesPathCommand(
        Long userId,
        Pageable pageable
) {
}
