package com.aoo.admin.application.service.soundsource;

import com.aoo.admin.application.port.in.soundsource.DeleteSoundSourceUseCase;
import com.aoo.admin.application.port.out.soundsource.DeleteSoundSourcePort;
import com.aoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.item.soundsource.SoundSource;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteSoundSourceService implements DeleteSoundSourceUseCase {

    private final FindSoundSourcePort findSoundSourcePort;
    private final DeleteSoundSourcePort deleteSoundSourcePort;
    private final DeleteFileUseCase deleteFileUseCase;

    @Override
    @Transactional
    public MessageDto deleteSoundSource(Long id) {

        SoundSource soundSource = findSoundSourcePort.loadSoundSource(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_SOURCE_NOT_FOUND));

        deleteFileUseCase.deleteFile(soundSource.getFile().getFileId().getId());

        deleteSoundSourcePort.deleteSoundSource(soundSource);

        return new MessageDto(id + "번 음원이 삭제되었습니다.");
    }
}
