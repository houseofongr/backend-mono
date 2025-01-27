package com.hoo.aoo.admin.application.service.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.UpdateSoundSourceCommand;
import com.hoo.aoo.admin.application.port.in.soundsource.UpdateSoundSourceUseCase;
import com.hoo.aoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.aoo.admin.application.port.out.soundsource.UpdateSoundSourcePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateSoundSourceService implements UpdateSoundSourceUseCase {

    private final FindSoundSourcePort findSoundSourcePort;
    private final UpdateSoundSourcePort updateSoundSourcePort;

    @Override
    @Transactional
    public MessageDto updateSoundSource(Long soundSourceId, UpdateSoundSourceCommand command) {

        SoundSource soundSource = findSoundSourcePort.loadSoundSource(soundSourceId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_SOURCE_NOT_FOUND));

        soundSource.updateDetail(command.name(), command.description(), command.isActive());

        updateSoundSourcePort.updateSoundSource(soundSource);

        return new MessageDto(soundSourceId + "번 음원의 정보가 수정되었습니다.");
    }
}
