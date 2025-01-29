package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.out.database.DeleteFilePort;
import com.hoo.aoo.file.application.port.out.database.FindFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.EraseFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteFileServiceTest {

    DeleteFileService sut;

    FindFilePort findFilePort;
    DeleteFilePort deleteFilePort;
    EraseFilePort eraseFilePort;

    @BeforeEach
    void init() {
        findFilePort = mock();
        deleteFilePort = mock();
        eraseFilePort = mock();
        sut = new DeleteFileService(findFilePort, deleteFilePort, eraseFilePort);
    }

    @Test
    @DisplayName("파일 삭제 서비스 테스트")
    void testDeleteFile(@TempDir Path tempDir) throws IOException {
        // given
        Long id = 1L;
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());

        // when
        when(findFilePort.load(id)).thenReturn(Optional.of(file));
        sut.deleteFile(id);

        // then
        verify(eraseFilePort, times(1)).erase(file);
        verify(deleteFilePort, times(1)).deleteFile(id);
    }
}