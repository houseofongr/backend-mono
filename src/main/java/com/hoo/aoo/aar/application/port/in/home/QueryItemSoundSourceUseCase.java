package com.hoo.aoo.aar.application.port.in.home;

public interface QueryItemSoundSourceUseCase {
    QueryItemSoundSourcesResult queryItemSoundSources(Long userId, Long homeId, Long itemId);
}
