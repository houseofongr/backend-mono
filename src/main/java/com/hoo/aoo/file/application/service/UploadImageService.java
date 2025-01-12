package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadImageUseCase;
import com.hoo.aoo.file.application.port.out.database.SavePublicImageFilePort;
import com.hoo.aoo.file.domain.*;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadImageUseCase {

    private final SavePublicImageFilePort savePublicImageFilePort;
    private final FileAttribute fileAttribute;

    @Override
    @Transactional
    public UploadImageResult upload(List<MultipartFile> images) {

        List<UploadImageResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : images) {

            try {
                String originalFilename = multipartFile.getOriginalFilename();

                String[] split = originalFilename.split("\\.");

                if (split.length < 2)
                    throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);

                String tempFileName = UUID.randomUUID() + "." + split[split.length - 1];

                FileId fileId = FileId.create(fileAttribute.getPublicImagePath(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE, originalFilename, tempFileName);
                FileSize fileSize = new FileSize(multipartFile.getSize(), fileAttribute.getFileSizeLimit());

                File file = File.create(fileId, FileStatus.CREATED, Owner.empty(), fileSize);

                java.io.File javaFile = new java.io.File(file.getFileId().getPath());

                javaFile.getParentFile().mkdirs();

                if (!javaFile.createNewFile())
                    throw new FileException(FileErrorCode.FILE_NAME_DUPLICATION);

                multipartFile.transferTo(javaFile);

                Long savedId = savePublicImageFilePort.save(file);

                fileInfos.add(new UploadImageResult.FileInfo(file, savedId));

            } catch (IOException e) {

                log.error(e.getMessage());
                throw new FileException(FileErrorCode.NEW_FILE_CREATION_FAILED);

            } catch (FileSizeLimitExceedException e) {

                log.error(e.getMessage());
                throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);
            } catch (FileExtensionMismatchException e) {

                log.error(e.getMessage());
                throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);
            }
        }

        return new UploadImageResult(fileInfos);
    }
}