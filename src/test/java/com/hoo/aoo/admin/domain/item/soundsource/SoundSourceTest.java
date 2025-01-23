package com.hoo.aoo.admin.domain.item.soundsource;

import com.hoo.aoo.admin.domain.file.FileType;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class SoundSourceTest {

    @Test
    @DisplayName("음원 생성 테스트")
    void testCreateSoundSource() {
        // given
        Long fileId = 1L;
        String name = "음원1";
        String description = "음원 설명";
        ZonedDateTime createdTime = ZonedDateTime.now();
        ZonedDateTime updatedTime = ZonedDateTime.now();

        // when
        SoundSource soundSource = SoundSource.create(fileId, name, description, createdTime, updatedTime, null);

        // then
        assertThat(soundSource.getFile().getFileId().getId()).isEqualTo(fileId);
        assertThat(soundSource.getFile().getFileType()).isEqualTo(FileType.AUDIO);
        assertThat(soundSource.getDetail().getName()).isEqualTo(name);
        assertThat(soundSource.getDetail().getDescription()).isEqualTo(description);
        assertThat(soundSource.getBaseTime().getCreatedTime()).isCloseTo(createdTime, new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
        assertThat(soundSource.getBaseTime().getUpdatedTime()).isCloseTo(updatedTime, new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
        assertThat(soundSource.getActive().isActive()).isTrue();
    }

}