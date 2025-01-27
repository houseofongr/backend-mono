package com.hoo.aoo.admin.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float z;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Long imageFileId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOUSE_ID")
    private HouseJpaEntity house;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<ItemJpaEntity> items;

    public void updateInfo(String name) {
        this.name = name;
    }

}
