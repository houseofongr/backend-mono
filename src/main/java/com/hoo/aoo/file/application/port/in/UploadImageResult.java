package com.hoo.aoo.file.application.port.in;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.File;

import java.util.List;

public record UploadImageResult(
        List<FileInfo> fileInfos
) {
    public record FileInfo(
            Long id,
            String name,
            String size,
            Authority authority
    ) {
        public FileInfo(File file, Long id) {
            this(id, file.getFileId().getFileSystemName(), file.getSize().getUnitSize(), file.getFileId().getAuthority());
        }
    }
}
