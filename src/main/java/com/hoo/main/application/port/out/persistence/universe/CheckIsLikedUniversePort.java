package com.hoo.main.application.port.out.persistence.universe;

public interface CheckIsLikedUniversePort {
    boolean checkIsLiked(Long universeId, Long userId);
}
