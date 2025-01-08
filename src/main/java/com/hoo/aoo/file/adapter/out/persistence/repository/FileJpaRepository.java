package com.hoo.aoo.file.adapter.out.persistence.repository;

import com.hoo.aoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {

    FileJpaEntity findByFileNameAndAbsolutePath(String fileName, String absolutePath);
}
