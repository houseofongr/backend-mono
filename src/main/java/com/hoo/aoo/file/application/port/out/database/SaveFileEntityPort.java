package com.hoo.aoo.file.application.port.out.database;

import com.hoo.aoo.file.domain.File;

public interface SaveFileEntityPort {
    Long save(File file);
}
