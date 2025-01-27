package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import org.springframework.stereotype.Component;

@Component
public class SoundSourceMapper {

    public SoundSource mapToDomainEntity(SoundSourceJpaEntity soundSourceJpaEntity) {
        return SoundSource.load(
                soundSourceJpaEntity.getId(),
                soundSourceJpaEntity.getItem().getId(),
                soundSourceJpaEntity.getAudioFileId(),
                soundSourceJpaEntity.getName(),
                soundSourceJpaEntity.getDescription(),
                soundSourceJpaEntity.getCreatedTime(),
                soundSourceJpaEntity.getUpdatedTime(),
                soundSourceJpaEntity.getIsActive()
        );
    }
}
