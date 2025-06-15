package com.aoo.admin.adapter.out.persistence.mapper;

import com.aoo.admin.domain.universe.piece.sound.Sound;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class SoundMapper {

    public Sound mapToDomainEntity(SoundJpaEntity soundJpaEntity) {
        return Sound.loadWithoutRelation(soundJpaEntity.getId(),
                soundJpaEntity.getAudioFileId(),
                soundJpaEntity.getTitle(),
                soundJpaEntity.getDescription(),
                soundJpaEntity.getHidden(),
                soundJpaEntity.getCreatedTime(),
                soundJpaEntity.getUpdatedTime());
    }
}
