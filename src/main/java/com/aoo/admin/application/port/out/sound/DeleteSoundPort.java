package com.aoo.admin.application.port.out.sound;

import com.aoo.admin.domain.universe.piece.sound.Sound;

import java.util.List;

public interface DeleteSoundPort {
    void delete(Long id);
    void deleteAll(List<Long> ids);
}
