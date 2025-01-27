package com.hoo.aoo.admin.application.service.soundsource;

import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceResult;
import com.hoo.aoo.admin.application.port.in.soundsource.QuerySoundSourceUseCase;
import com.hoo.aoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuerySoundSourceService implements QuerySoundSourceUseCase {

    private final FindSoundSourcePort findSoundSourcePort;

    @Override
    @Transactional(readOnly = true)
    public QuerySoundSourceResult querySoundSource(Long id) {

        SoundSource soundSource = findSoundSourcePort.loadSoundSource(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.SOUND_SOURCE_NOT_FOUND));

        return QuerySoundSourceResult.of(soundSource);
    }
}
