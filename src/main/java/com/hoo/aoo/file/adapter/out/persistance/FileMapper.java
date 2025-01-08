package com.hoo.aoo.file.adapter.out.persistance;

import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.file.adapter.out.persistance.entity.FileJpaEntity;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileStatus;
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
                owner);
    }
}
