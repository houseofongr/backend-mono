package com.hoo.aoo.file.adapter.out.persistence;

import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.aoo.file.application.port.out.database.FindFilePort;
import com.hoo.aoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SaveImageFilePort, FindFilePort {

    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public Long save(File file) {

        FileJpaEntity newEntity = FileJpaEntity.create(file);
        fileJpaRepository.save(newEntity);

        return newEntity.getId();
    }

    @SneakyThrows({FileSizeLimitExceedException.class, FileExtensionMismatchException.class, IllegalFileTypeDirException.class, IllegalFileAuthorityDirException.class})
    @Override
    public Optional<File> find(Long fileId) {

        Optional<FileJpaEntity> optional = fileJpaRepository.findById(fileId);

        if (optional.isPresent())
            return Optional.of(fileMapper.mapToDomainEntity(optional.get()));
        else
            return Optional.empty();
    }
}
