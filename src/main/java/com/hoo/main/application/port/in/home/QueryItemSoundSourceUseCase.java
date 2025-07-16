package com.hoo.main.application.port.in.home;

public interface QueryItemSoundSourceUseCase {
    QueryItemSoundSourcesResult queryItemSoundSources(Long userId, Long itemId);
}
