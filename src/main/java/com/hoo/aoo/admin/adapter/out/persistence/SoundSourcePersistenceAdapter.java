package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.aoo.admin.application.port.out.soundsource.SaveSoundSourcePort;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoundSourcePersistenceAdapter implements SaveSoundSourcePort {

    private final SoundSourceJpaRepository soundSourceJpaRepository;

    @Override
    public Long saveSoundSource(SoundSource soundSource) {
        SoundSourceJpaEntity soundSourceJpaEntity = SoundSourceJpaEntity.create(soundSource);
        soundSourceJpaRepository.save(soundSourceJpaEntity);

        return soundSourceJpaEntity.getId();
    }
}
