package com.aoo.admin.application.port.in.soundsource;

import com.aoo.common.application.port.in.MessageDto;

public interface DeleteSoundSourceUseCase {
    MessageDto deleteSoundSource(Long id);
}
