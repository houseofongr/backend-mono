package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
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
public class HouseJpaEntity extends DateColumnBaseEntity {

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
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Long basicImageFileId;

    @Column(nullable = false)
    private Long borderImageFileId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.PERSIST)
    private List<RoomJpaEntity> rooms;

    public void updateInfo(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }
}
