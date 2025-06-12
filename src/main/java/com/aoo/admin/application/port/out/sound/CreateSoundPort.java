package com.aoo.admin.application.port.out.sound;

import com.aoo.admin.application.port.in.sound.CreateSoundCommand;
import com.aoo.admin.domain.universe.piece.sound.Sound;

public interface CreateSoundPort {
    Sound createSound(Long audioId, CreateSoundCommand command);
}
