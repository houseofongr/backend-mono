package com.hoo.aoo.file.adapter.out.persistence;

import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.file.application.service.FileAttribute;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.domain.*;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileMapper {

    private final FileAttribute fileAttribute;

    public File mapToDomainEntity(FileJpaEntity fileJpaEntity) throws FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {

        FileId fileId = FileId.load(fileJpaEntity.getAbsolutePath(), fileJpaEntity.getRealFileName(), fileJpaEntity.getFileSystemName());

        FileStatus fileStatus = fileJpaEntity.getIsDeleted() ? FileStatus.DELETED : FileStatus.CREATED;

        Owner owner = fileJpaEntity.getOwner() == null ? null : new Owner(fileJpaEntity.getOwner().getId());

        FileSize fileSize = new FileSize(fileJpaEntity.getFileSize(), fileAttribute.getFileSizeLimit());

        return File.create(fileId, fileStatus, owner, fileSize);
    }
}
