package com.hoo.aoo.admin.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "HOUSE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String author;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Long basicImageFileId;

    @Column(nullable = false)
    private Long borderImageFileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.PERSIST)
    private List<RoomJpaEntity> rooms;

}
