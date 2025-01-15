package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "HOUSE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 255)
    private String title;

    @Setter
    @Column(nullable = false, length = 255)
    private String author;

    @Setter
    @Column(nullable = false, length = 255)
    private String description;

    @Setter
    @Column(nullable = false)
    private Float width;

    @Setter
    @Column(nullable = false)
    private Float height;

    @Setter
    @Column(nullable = false)
    private Long basicImageFileId;

    @Setter
    @Column(nullable = false)
    private Long borderImageFileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.PERSIST)
    private List<RoomJpaEntity> rooms;

}
