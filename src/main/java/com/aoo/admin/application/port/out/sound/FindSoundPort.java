package com.aoo.admin.application.port.out.sound;

import com.aoo.admin.domain.universe.piece.sound.Sound;

public interface FindSoundPort {
    Sound find(Long id);
}
