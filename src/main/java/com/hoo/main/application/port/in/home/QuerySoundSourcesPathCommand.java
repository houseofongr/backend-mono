package com.hoo.main.application.port.in.home;

import org.springframework.data.domain.Pageable;

public record QuerySoundSourcesPathCommand(
        Long userId,
        Pageable pageable
) {
}
