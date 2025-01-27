package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.aoo.admin.application.port.out.soundsource.FindSoundSourcePort;
import com.hoo.aoo.admin.application.port.out.soundsource.SaveSoundSourcePort;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SoundSourcePersistenceAdapter implements SaveSoundSourcePort, FindSoundSourcePort {

    private final SoundSourceJpaRepository soundSourceJpaRepository;
    private final SoundSourceMapper soundSourceMapper;

    @Override
    public Long saveSoundSource(SoundSource soundSource) {
        SoundSourceJpaEntity soundSourceJpaEntity = SoundSourceJpaEntity.create(soundSource);
        soundSourceJpaRepository.save(soundSourceJpaEntity);

        return soundSourceJpaEntity.getId();
    }

    @Override
    public Optional<SoundSource> loadSoundSource(Long id) {
        return soundSourceJpaRepository.findById(id)
                .map(soundSourceMapper::mapToDomainEntity);
    }
}
