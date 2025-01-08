package com.hoo.aoo.file.adapter.out.persistance;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.persistance.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistance.repository.FileJpaRepository;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
import com.hoo.aoo.file.domain.FileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({FilePersistenceAdapter.class, FileMapper.class})
class FilePersistenceAdapterTest {

    @Autowired
    FilePersistenceAdapter sut;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("파일 엔티티 저장")
    void testSavePublicFile() {
        // given
        File file = FileF.IMAGE_FILE_1.get("/tmp");

        // when
        Long id = sut.savePublicFile(file);

        FileJpaEntity entityInDB = fileJpaRepository.findByFileNameAndAbsolutePath(file.getFileId().getFileName(), file.getFileId().getDirectory());

        // then
        assertThat(entityInDB.getId()).isEqualTo(id);
        assertThat(entityInDB.getAuthority()).isEqualTo(Authority.PUBLIC);
        assertThat(entityInDB.getFileType()).isEqualTo(FileType.IMAGE);
        assertThat(entityInDB.getIsDeleted()).isFalse();
        assertThat(entityInDB.getCreatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(entityInDB.getUpdatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
    }
}