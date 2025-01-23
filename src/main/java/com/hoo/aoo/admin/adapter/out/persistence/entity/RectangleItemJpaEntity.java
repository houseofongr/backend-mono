package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class RectangleItemJpaEntity extends ItemJpaEntity {

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float width;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float angle;

    public RectangleItemJpaEntity(Long id, String name, HomeJpaEntity home, RoomJpaEntity room, UserJpaEntity user, List<SoundSourceJpaEntity> soundSources, Float x, Float y, Float height, Float width, Float angle) {
        super(id, name, home, room, user, soundSources);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
    }
}
