package com.hoo.aoo.file.application.port.in;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.File;

import java.util.List;

public record UploadFileResult(
        List<FileInfo> fileInfos
) {
    public record FileInfo(
            Long id,
            Long ownerId,
            String name,
            String size,
            Authority authority
    ) {
        public FileInfo(File file, Long id) {
            this(
                    id,
                    file.getOwner().getId(),
                    file.getFileId().getFileSystemName(),
                    file.getSize().getUnitSize(),
                    file.getFileId().getAuthority()
            );
        }
    }
}
