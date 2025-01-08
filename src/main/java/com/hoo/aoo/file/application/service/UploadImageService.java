package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import com.hoo.aoo.file.application.port.out.database.SavePublicImageFilePersistencePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileId;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {

    private final SavePublicImageFilePersistencePort savePublicImageFilePersistencePort;
    private final FileAttribute fileAttribute;

    @Override
    @Transactional
    public UploadImageResult upload(List<MultipartFile> images) {

        List<UploadImageResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : images) {

            try {
                FileId fileId = new FileId(fileAttribute.getPublicImagePath(), multipartFile.getOriginalFilename());

                File file = File.createImageFile(fileId, multipartFile.getSize());

                file.retrieve();

                if (!file.getJavaFile().createNewFile())
                    throw new FileException(FileErrorCode.FILE_NAME_DUPLICATION);

                multipartFile.transferTo(file.getJavaFile());

                Long savedId = savePublicImageFilePersistencePort.save(file);

                fileInfos.add(new UploadImageResult.FileInfo(file, savedId));

            } catch (IOException e) {

                log.error(e.getMessage());
                throw new FileException(FileErrorCode.NEW_FILE_CREATION_FAILED);

            } catch (FileSizeLimitExceedException e) {

                log.error(e.getMessage());
                throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);
            }
        }

        return new UploadImageResult(fileInfos);
    }
}