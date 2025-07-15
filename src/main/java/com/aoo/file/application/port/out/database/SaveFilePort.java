package com.aoo.file.application.port.out.database;

import com.aoo.file.domain.File;

public interface SaveFilePort {
    Long save(File file);
}
