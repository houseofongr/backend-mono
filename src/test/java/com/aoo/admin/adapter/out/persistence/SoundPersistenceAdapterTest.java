package com.aoo.admin.adapter.out.persistence;

import com.aoo.admin.domain.universe.piece.sound.Sound;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.aoo.common.adapter.out.persistence.entity.SoundJpaEntity;
import com.aoo.common.adapter.out.persistence.repository.SoundJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@PersistenceAdapterTest
@Sql("SoundPersistenceAdapter.sql")
@Import(SoundPersistenceAdapter.class)
class SoundPersistenceAdapterTest {

    @Autowired
    SoundPersistenceAdapter sut;

    @Autowired
    SoundJpaRepository soundJpaRepository;

    @Test
    @DisplayName("사운드 저장 테스트")
    void testSaveSound() {
        // given
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.");

        // when
        Long newSoundId = sut.save(sound);
        SoundJpaEntity soundInDB = soundJpaRepository.findById(newSoundId).orElseThrow();

        // then
        assertThat(soundInDB.getId()).isEqualTo(newSoundId);
        assertThat(soundInDB.getAudioFileId()).isEqualTo(345L);
        assertThat(soundInDB.getPiece().getId()).isEqualTo(1L);
        assertThat(soundInDB.getTitle()).isEqualTo("소리");
        assertThat(soundInDB.getDescription()).isEqualTo("사운드는 소리입니다.");
    }

}