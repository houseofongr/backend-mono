package com.aoo.file.adapter.out.persistence.repository;

import com.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {

    FileJpaEntity findByFileSystemNameAndAbsolutePath(String fileSystemName, String absolutePath);
}
