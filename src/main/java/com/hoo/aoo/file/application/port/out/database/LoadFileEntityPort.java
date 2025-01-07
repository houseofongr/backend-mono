package com.hoo.aoo.file.application.port.out.database;

import com.hoo.aoo.file.adapter.out.persistance.entity.FileJpaEntity;

public interface LoadFileEntityPort {
    FileJpaEntity load(Long fileId);
}
