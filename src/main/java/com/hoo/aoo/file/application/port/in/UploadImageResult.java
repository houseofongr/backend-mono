package com.hoo.aoo.file.application.port.in;

import com.hoo.aoo.file.domain.File;

import java.util.List;

public record UploadImageResult(
        List<FileInfo> fileInfos
) {
    public record FileInfo(
            Long id,
            String name,
            String size
    ) {
        public FileInfo(File file, Long id) {
            this(id, file.getFileId().getFileName(), file.getSize().getUnitSize());
        }
    }
}
