package com.hoo.aoo.file.adapter.out.persistence;

import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.application.port.out.database.LoadPublicImageFilePort;
import com.hoo.aoo.file.application.port.out.database.SavePublicImageFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SavePublicImageFilePort, LoadPublicImageFilePort {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public Long save(File file) {

        FileJpaEntity newEntity = fileMapper.mapToNewJpaEntity(file, null);

        fileJpaRepository.save(newEntity);

        return newEntity.getId();
    }

    @Override
    public Optional<File> load(Long fileId) throws FileSizeLimitExceedException {

        Optional<FileJpaEntity> optional = fileJpaRepository.findById(fileId);

        if (optional.isEmpty()) return Optional.empty();

        FileJpaEntity fileJpaEntity = optional.get();

        return Optional.of(fileMapper.mapToDomainEntity(fileJpaEntity));
    }
}
