package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CIRCLE_ITEM")
@DiscriminatorValue("CIRCLE")
@Getter
@NoArgsConstructor
public class CircleItemJpaEntity extends ItemJpaEntity {

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float radius;

    public CircleItemJpaEntity(Long id, String name, HomeJpaEntity home, RoomJpaEntity room, UserJpaEntity user, List<SoundSourceJpaEntity> soundSources, Float x, Float y, Float radius) {
        super(id, name, home, room, user, soundSources);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}
