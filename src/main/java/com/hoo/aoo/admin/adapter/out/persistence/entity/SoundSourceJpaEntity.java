package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOUND_SOURCE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoundSourceJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 255)
    private Long audioFileId;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private ItemJpaEntity item;

}
