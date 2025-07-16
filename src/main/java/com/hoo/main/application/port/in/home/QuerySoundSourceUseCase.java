package com.hoo.main.application.port.in.home;

public interface QuerySoundSourceUseCase {
    QuerySoundSourceResult querySoundSource(Long userId, Long soundSourceId);
}
