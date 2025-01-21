package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.aoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.aoo.file.application.port.out.filesystem.WriteFilePort;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadImageService implements UploadPublicImageUseCase, UploadPrivateImageUseCase {

    private final FileAttribute fileAttribute;
    private final SaveImageFilePort saveImageFilePort;
    private final WriteFilePort writeFilePort;
    private final RandomFileNamePort randomFileNamePort;

    @Override
    @Transactional
    public UploadImageResult publicUpload(List<MultipartFile> images) {
        return upload(images, new BasicFileIdCreateStrategy(fileAttribute.getBaseDir(), Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    @Transactional
    public UploadImageResult privateUpload(List<MultipartFile> images) {
        return upload(images, new BasicFileIdCreateStrategy(fileAttribute.getBaseDir(), Authority.PRIVATE_FILE_ACCESS, FileType.IMAGE));
    }

    @Override
    @Transactional
    public UploadImageResult privateUpload(MultipartFile image) {
        return privateUpload(List.of(image));
    }

    private UploadImageResult upload(List<MultipartFile> images, FileIdCreateStrategy fileIdCreateStrategy) {

        List<UploadImageResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : images) {

            try {
                String originalFilename = multipartFile.getOriginalFilename();
                if (originalFilename == null) throw new FileException(FileErrorCode.FILE_NAME_EMPTY);

                String fileSystemName = randomFileNamePort.getName(originalFilename);

                FileId fileId = fileIdCreateStrategy.create(originalFilename, fileSystemName);
                FileSize fileSize = new FileSize(multipartFile.getSize(), fileAttribute.getFileSizeLimit());
                File file = File.create(fileId, FileStatus.CREATED, Owner.empty(), fileSize);

                writeFilePort.write(file, multipartFile);

                Long savedId = saveImageFilePort.save(file);

                fileInfos.add(new UploadImageResult.FileInfo(file, savedId));

            } catch (IOException e) {
                throw new FileException(FileErrorCode.NEW_FILE_CREATION_FAILED);

            } catch (FileSizeLimitExceedException e) {
                throw new FileException(FileErrorCode.FILE_SIZE_LIMIT_EXCEED);

            } catch (FileExtensionMismatchException e) {
                throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);

            }
        }

        return new UploadImageResult(fileInfos);
    }
}