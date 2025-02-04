package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.common.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.aoo.common.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({SoundSourcePersistenceAdapter.class, SoundSourceMapper.class})
class SoundSourcePersistenceAdapterTest {

    @Autowired
    SoundSourcePersistenceAdapter sut;

    @Autowired
    SoundSourceJpaRepository soundSourceJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Sql("SoundSourcePersistenceAdapterTest.sql")
    @DisplayName("음원 저장 테스트")
    void testSaveSoundSource() {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골송", "2025년 골골송 V1", true);

        // when
        Long savedSoundSourceId = sut.saveSoundSource(soundSource);

        Optional<SoundSourceJpaEntity> optional = soundSourceJpaRepository.findById(savedSoundSourceId);
        assertThat(optional).isNotEmpty();
        SoundSourceJpaEntity soundSourceJpaEntity = optional.get();

        // then
        assertThat(soundSourceJpaEntity.getItem().getId()).isEqualTo(1L);
        assertThat(soundSourceJpaEntity.getItem().getSoundSources().getLast()).usingRecursiveComparison().isEqualTo(soundSourceJpaEntity);

        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getSoundSourceDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getSoundSourceDetail().getDescription());
        assertThat(soundSourceJpaEntity.getIsActive()).isEqualTo(soundSource.getActive().isActive());
        assertThat(soundSourceJpaEntity.getAudioFileId()).isEqualTo(soundSource.getFile().getFileId().getId());
        assertThat(savedSoundSourceId).isNotNull();
    }

    @Test
    @Sql("SoundSourcePersistenceAdapterTest.sql")
    @DisplayName("음원 조회 테스트")
    void testLoadSoundSource() {
        // given
        Long soundSourceId = 1L;

        // when
        Optional<SoundSource> soundSource = sut.loadSoundSource(soundSourceId);
        Optional<SoundSource> nullSound = sut.loadSoundSource(1234L);

        // then
        assertThat(nullSound).isEmpty();
        assertThat(soundSource).isNotEmpty();
        assertThat(soundSource.get().getSoundSourceDetail().getName()).isEqualTo("골골송");
    }

    @Test
    @Sql("SoundSourcePersistenceAdapterTest.sql")
    @DisplayName("음원 수정 테스트")
    void testUpdateSoundSource() throws InterruptedException {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골골송", "2026년 설이가 보내는 골골골송", false);

        // when
        sut.updateSoundSource(soundSource);
        entityManager.flush();
        entityManager.clear();

        Optional<SoundSourceJpaEntity> optional = soundSourceJpaRepository.findById(1L);
        assertThat(optional).isNotEmpty();
        SoundSourceJpaEntity soundSourceInDB = optional.get();

        // then
        assertThat(soundSourceInDB.getName()).isEqualTo("골골골송");
        assertThat(soundSourceInDB.getDescription()).isEqualTo("2026년 설이가 보내는 골골골송");
        assertThat(soundSourceInDB.getIsActive()).isFalse();
    }

    @Test
    @Sql("SoundSourcePersistenceAdapterTest.sql")
    @DisplayName("음원 삭제 테스트")
    void testDeleteSoundSource() {
        // given
        SoundSource soundSource = SoundSource.create(1L, 1L, 1L, "골골골송", "2026년 설이가 보내는 골골골송", false);

        // when
        sut.deleteSoundSource(soundSource);

        // then
        assertThat(soundSourceJpaRepository.findById(1L)).isEmpty();
    }
}