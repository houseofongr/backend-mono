package com.hoo.aoo.file.adapter.out.filesystem;

import com.hoo.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.aoo.file.application.port.out.filesystem.WriteFilePort;
import com.hoo.aoo.file.application.service.FileErrorCode;
import com.hoo.aoo.file.application.service.FileException;
import com.hoo.aoo.file.domain.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class FileSystemAdapter implements WriteFilePort, RandomFileNamePort {

    @Override
    public void write(File file, MultipartFile multipartFile) throws IOException {
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());

        javaFile.getParentFile().mkdirs();

        if (!javaFile.createNewFile())
            throw new FileException(FileErrorCode.FILE_NAME_DUPLICATION);

        multipartFile.transferTo(javaFile);
    }

    @Override
    public String getName(String originalFileName) {

        String[] split = originalFileName.split("\\.");

        if (split.length < 2)
            throw new FileException(FileErrorCode.INVALID_FILE_EXTENSION);

        return UUID.randomUUID() + "." + split[split.length - 1];
    }
}
