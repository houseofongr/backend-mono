package com.aoo.file.application.port.out.database;

import com.aoo.file.domain.File;

public interface SaveImageFilePort {
    Long save(File file);
}
