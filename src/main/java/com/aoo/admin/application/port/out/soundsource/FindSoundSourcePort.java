package com.aoo.admin.application.port.out.soundsource;

import com.aoo.admin.domain.item.soundsource.SoundSource;

import java.util.Optional;

public interface FindSoundSourcePort {
    Optional<SoundSource> loadSoundSource(Long id);
}
