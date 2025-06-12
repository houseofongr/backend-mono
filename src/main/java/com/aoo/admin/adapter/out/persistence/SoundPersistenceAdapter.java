package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.application.port.out.sound.SaveSoundPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.piece.sound.Sound;
import com.aoo.common.adapter.out.persistence.entity.PieceJpaEntity;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.PieceJpaRepository;
import com.aoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SoundPersistenceAdapter implements SaveSoundPort {

    private final PieceJpaRepository pieceJpaRepository;
    private final SoundJpaRepository soundJpaRepository;

    @Override
    public Long save(Sound sound) {
        Long pieceId = sound.getBasicInfo().getPieceId();
        PieceJpaEntity pieceJpaEntity = pieceJpaRepository.findById(pieceId).orElseThrow(() -> new AdminException(AdminErrorCode.PIECE_NOT_FOUND));
        SoundJpaEntity soundJpaEntity = SoundJpaEntity.create(sound, pieceJpaEntity);

        soundJpaRepository.save(soundJpaEntity);

        return soundJpaEntity.getId();
    }
}
