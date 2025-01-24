package com.hoo.aoo.admin.application.port.out.soundsource;

import com.hoo.aoo.admin.domain.soundsource.SoundSource;

public interface CreateSoundSourcePort {
    SoundSource createSoundSource(Long audioFileId, String name, String description, Boolean active);
}
