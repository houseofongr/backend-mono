package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.SoundSourceJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.repository.SoundSourceJpaRepository;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(SoundSourcePersistenceAdapter.class)
class SoundSourcePersistenceAdapterTest {

    @Autowired
    SoundSourcePersistenceAdapter sut;

    @Autowired
    SoundSourceJpaRepository soundSourceJpaRepository;

    @Test
    @DisplayName("음원 저장 테스트")
    void testSaveSoundSource() {
        // given
        SoundSource soundSource = MockEntityFactoryService.getSoundSource();

        // when
        Long savedSoundSourceId = sut.saveSoundSource(soundSource);

        Optional<SoundSourceJpaEntity> optional = soundSourceJpaRepository.findById(savedSoundSourceId);
        assertThat(optional).isNotEmpty();
        SoundSourceJpaEntity soundSourceJpaEntity = optional.get();

        // then
        assertThat(soundSourceJpaEntity.getName()).isEqualTo(soundSource.getSoundSourceDetail().getName());
        assertThat(soundSourceJpaEntity.getDescription()).isEqualTo(soundSource.getSoundSourceDetail().getDescription());
        assertThat(soundSourceJpaEntity.getIsActive()).isEqualTo(soundSource.getActive().isActive());
        assertThat(soundSourceJpaEntity.getAudioFileId()).isEqualTo(soundSource.getFile().getFileId().getId());
        assertThat(savedSoundSourceId).isNotNull();
    }

}