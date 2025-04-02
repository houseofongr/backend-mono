package com.hoo.aoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "SPACE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceJpaEntity extends DateColumnBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Long imageFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIVERSE_ID")
    private UniverseJpaEntity universe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_SPACE_ID")
    private SpaceJpaEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<SpaceJpaEntity> children;

}
