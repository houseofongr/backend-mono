package com.hoo.aoo.file.application.port.out.filesystem;

import com.hoo.aoo.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface StoreImageFileSystemPort {
    File storePublicFile(MultipartFile file);
}
