package com.hoo.aoo.file.adapter.out.persistance;

import com.hoo.aoo.file.adapter.out.persistance.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistance.repository.FileJpaRepository;
import com.hoo.aoo.file.application.port.out.database.SaveFilePersistencePort;
import com.hoo.aoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SaveFilePersistencePort {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public Long savePublicFile(File file) {

        FileJpaEntity newEntity = fileMapper.mapToNewJpaEntity(file, null);

        fileJpaRepository.save(newEntity);

        return newEntity.getId();
    }
}
