package com.hoo.file.application.port.out.database;

import com.hoo.file.domain.File;

public interface SaveFilePort {
    Long save(File file);
}
