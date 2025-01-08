package com.hoo.aoo.file.adapter.out.persistence.entity;

import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.domain.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 255)
    private String absolutePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(nullable = false, length = 255)
    private Boolean isDeleted;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(nullable = false)
    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private UserJpaEntity owner;

}
