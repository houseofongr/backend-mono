package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.aoo.file.application.port.out.filesystem.WriteFilePort;
import com.hoo.aoo.file.domain.*;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class UploadService {

    private final FileAttribute fileAttribute;
    private final SaveImageFilePort saveImageFilePort;
    private final WriteFilePort writeFilePort;
    private final RandomFileNamePort randomFileNamePort;

    UploadFileResult upload(List<MultipartFile> files, FileIdCreateStrategy fileIdCreateStrategy) {
        return upload(files, null, fileIdCreateStrategy);
    }

    UploadFileResult upload(List<MultipartFile> files, Long ownerId, FileIdCreateStrategy idCreateStrategy) {

        List<UploadFileResult.FileInfo> fileInfos = new ArrayList<>();

        for (MultipartFile multipartFile : files) {

            try {
                String originalFilename = multipartFile.getOriginalFilename();
                if (originalFilename == null) throw new FileException(FileErrorCode.FILE_NAME_EMPTY);

                String fileSystemName = randomFileNamePort.getName(originalFilename);

                FileId fileId = idCreateStrategy.create(originalFilename, fileSystemName);
                FileSize fileSize = new FileSize(multipartFile.getSize(), fileAttribute.getFileSizeLimit());
                Owner owner = new Owner(ownerId);

                File file = File.create(fileId, FileStatus.CREATED, owner, fileSize);

                writeFilePort.write(file, multipartFile);

                Long savedId = saveImageFilePort.save(file);

                fileInfos.add(UploadFileResult.FileInfo.from(file, savedId));

            } catch (IOException e) {
                throw new FileException(e, FileErrorCode.NEW_FILE_CREATION_FAILED);

            } catch (FileSizeLimitExceedException e) {
                throw new FileException(e, FileErrorCode.FILE_SIZE_LIMIT_EXCEED);

            } catch (FileExtensionMismatchException e) {
                throw new FileException(e, FileErrorCode.INVALID_FILE_EXTENSION);

            }
        }

        return new UploadFileResult(fileInfos);
    }
}
