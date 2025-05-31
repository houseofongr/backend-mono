package com.aoo.file.adapter.out.persistence;

import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.aoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.aoo.file.application.port.out.database.DeleteFilePort;
import com.aoo.file.application.port.out.database.FindFilePort;
import com.aoo.file.application.port.out.database.SaveImageFilePort;
import com.aoo.file.domain.File;
import com.aoo.file.domain.exception.FileExtensionMismatchException;
import com.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.aoo.file.domain.exception.IllegalFileTypeDirException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilePersistenceAdapter implements SaveImageFilePort, FindFilePort, DeleteFilePort {

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
    public Optional<File> load(Long fileId) {

        Optional<FileJpaEntity> optional = fileJpaRepository.findById(fileId);

        if (optional.isPresent())
            return Optional.of(fileMapper.mapToDomainEntity(optional.get()));
        else
            return Optional.empty();
    }

    @Override
    public void deleteFile(Long id) {
        fileJpaRepository.deleteById(id);
    }
}
