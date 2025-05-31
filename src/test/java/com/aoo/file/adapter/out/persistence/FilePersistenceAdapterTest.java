package com.aoo.file.adapter.out.persistence;

import com.aoo.common.domain.Authority;
import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.aoo.file.application.service.FileProperties;
import com.aoo.file.domain.File;
import com.aoo.file.domain.FileF;
import com.aoo.file.domain.FileStatus;
import com.aoo.file.domain.FileType;
import com.aoo.file.domain.exception.FileExtensionMismatchException;
import com.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.aoo.file.domain.exception.IllegalFileTypeDirException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({FilePersistenceAdapter.class, FileMapper.class, FileProperties.class})
class FilePersistenceAdapterTest {

    @Autowired
    FilePersistenceAdapter sut;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("파일 엔티티 저장")
    void testSave() {
        // given
        File file = FileF.IMAGE_FILE_1.get("/tmp");

        // when
        Long id = sut.save(file);

        FileJpaEntity entityInDB = fileJpaRepository.findByFileSystemNameAndAbsolutePath(file.getFileId().getFileSystemName(), file.getFileId().getDirectory());

        // then
        assertThat(entityInDB.getId()).isEqualTo(id);
        assertThat(entityInDB.getIsDeleted()).isFalse();
        assertThat(entityInDB.getCreatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
        assertThat(entityInDB.getUpdatedTime()).isCloseTo(ZonedDateTime.now(), within(1, ChronoUnit.SECONDS));
    }

    @Test
    @Sql("FilePersistenceAdapterTest.sql")
    @DisplayName("이미지 파일 엔티티 조회")
    void testLoadPublicImageFile() throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;

        // when
        Optional<File> optional = sut.load(fileId);

        // then
        assertThat(optional).isNotEmpty();

        File file = optional.get();

        assertThat(file.getFileId().getBaseDir()).isEqualTo("/tmp");
        assertThat(file.getFileId().getRealFileName()).isEqualTo("test.png");
        assertThat(file.getSize().getFileByte()).isEqualTo(1234);
        assertThat(file.getOwnerId()).isNull();
        assertThat(file.getStatus()).isEqualTo(FileStatus.CREATED);
        assertThat(file.getFileId().getFileType()).isEqualTo(FileType.IMAGE);
        assertThat(file.getFileId().getAuthority()).isEqualTo(Authority.PUBLIC_FILE_ACCESS);
    }

    @Test
    @Sql("FilePersistenceAdapterTest2.sql")
    @DisplayName("오디오 파일 엔티티 조회")
    void testLoadAudioFile() throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;

        // when
        Optional<File> optional = sut.load(fileId);

        // then
        assertThat(optional).isNotEmpty();

        File file = optional.get();

        assertThat(file.getFileId().getBaseDir()).isEqualTo("/tmp");
        assertThat(file.getFileId().getRealFileName()).isEqualTo("test.mp3");
        assertThat(file.getSize().getFileByte()).isEqualTo(1234);
        assertThat(file.getOwnerId()).isNull();
        assertThat(file.getStatus()).isEqualTo(FileStatus.CREATED);
        assertThat(file.getFileId().getFileType()).isEqualTo(FileType.AUDIO);
        assertThat(file.getFileId().getAuthority()).isEqualTo(Authority.ALL_PRIVATE_AUDIO_ACCESS);
    }

    @Test
    @Sql("FilePersistenceAdapterTest2.sql")
    @DisplayName("파일 엔티티 삭제")
    void deleteFileEntity() {
        // given
        Long id = 1L;

        // when
        sut.deleteFile(id);

        // then
        assertThat(fileJpaRepository.findById(id)).isEmpty();
    }
}