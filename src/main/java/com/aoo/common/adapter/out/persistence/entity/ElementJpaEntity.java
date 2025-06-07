package com.aoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ELEMENT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ElementJpaEntity extends DateColumnBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Float dx;

    @Column(nullable = false)
    private Float dy;

    @Column(nullable = false, name = "SCALE_X")
    private Float scaleX;

    @Column(nullable = false, name = "SCALE_Y")
    private Float scaleY;

    @Column(nullable = false)
    private Long innerImageFileId;

    @Column(nullable = false)
    private Integer depth;

    @Column(nullable = false)
    private Long universeId;

    @Column
    private Long parentSpaceId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "element")
    private List<SoundJpaEntity> sounds;

}
