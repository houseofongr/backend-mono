package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.aoo.file.application.port.out.database.DeleteFilePort;
import com.hoo.aoo.file.application.port.out.database.FindFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.EraseFilePort;
import com.hoo.aoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteFileService implements DeleteFileUseCase {

    private final FindFilePort findFilePort;
    private final DeleteFilePort deleteFilePort;
    private final EraseFilePort eraseFilePort;

    @Override
    public void deleteFile(Long id) {
        try {

            System.out.println("파일 삭제시도 : " + id);

            File file = findFilePort.load(id)
                    .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));

            eraseFilePort.erase(file);

            deleteFilePort.deleteFile(id);

            log.info("파일 삭제완료 : {}", file);

        } catch (IOException e) {
            throw new FileException(e, FileErrorCode.DELETE_FILE_FAILED);
        }
    }

}
