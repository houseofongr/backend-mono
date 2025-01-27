package com.hoo.aoo.admin.application.port.in.soundsource;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface UpdateSoundSourceUseCase {
    MessageDto updateSoundSource(Long soundSourceId, UpdateSoundSourceCommand command);
}
