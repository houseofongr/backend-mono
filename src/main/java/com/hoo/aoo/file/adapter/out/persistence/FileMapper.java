package com.hoo.aoo.file.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.domain.*;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileJpaEntity mapToNewJpaEntity(File file, UserJpaEntity owner) {
        return new FileJpaEntity(null,
                file.getFileId().getFileName(),
                file.getFileId().getDirectory(),
                file.getType(),
                file.getStatus() == FileStatus.DELETED,
                file.getAuthority(),
                file.getSize().getFileByte(),
                owner);
    }

    public File mapToDomainEntity(FileJpaEntity fileJpaEntity) throws FileSizeLimitExceedException {

        FileId fileId = new FileId(fileJpaEntity.getAbsolutePath(), fileJpaEntity.getFileName());

        FileStatus fileStatus = fileJpaEntity.getIsDeleted() ? FileStatus.DELETED : FileStatus.CREATED;

        Owner owner = fileJpaEntity.getOwner() == null ? null : new Owner(fileJpaEntity.getOwner().getId());

        FileSize size = new FileSize(fileJpaEntity.getFileSize());

        return new File(fileId, fileJpaEntity.getFileType(), fileStatus, owner, fileJpaEntity.getAuthority(), size);
    }
}
