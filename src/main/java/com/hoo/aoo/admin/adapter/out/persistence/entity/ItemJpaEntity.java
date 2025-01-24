package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.DateColumnBaseEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DiscriminatorOptions;

import java.util.List;

@Entity
@Table(name = "ITEM")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorOptions(force = true)
@DiscriminatorColumn
@NoArgsConstructor
public abstract class ItemJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_ID")
    private HomeJpaEntity home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private RoomJpaEntity room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserJpaEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    List<SoundSourceJpaEntity> soundSources;

    public ItemJpaEntity(Long id, String name, HomeJpaEntity home, RoomJpaEntity room, UserJpaEntity user, List<SoundSourceJpaEntity> soundSources) {
        this.id = id;
        this.name = name;
        this.soundSources = soundSources;
    }
}
