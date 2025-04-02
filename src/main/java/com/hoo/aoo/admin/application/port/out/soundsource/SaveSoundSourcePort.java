package com.hoo.aoo.admin.application.port.out.soundsource;

import com.hoo.aoo.admin.domain.item.soundsource.SoundSource;

public interface SaveSoundSourcePort {
    Long saveSoundSource(SoundSource soundSource);
}
