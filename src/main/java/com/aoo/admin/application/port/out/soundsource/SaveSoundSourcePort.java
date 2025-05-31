package com.aoo.admin.application.port.out.soundsource;

import com.aoo.admin.domain.item.soundsource.SoundSource;

public interface SaveSoundSourcePort {
    Long saveSoundSource(SoundSource soundSource);
}
