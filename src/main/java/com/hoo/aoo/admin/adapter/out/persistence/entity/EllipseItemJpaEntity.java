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
@Table(name = "ELLIPSE_ITEM")
@DiscriminatorValue("ELLIPSE")
@Getter
@NoArgsConstructor
public class EllipseItemJpaEntity extends ItemJpaEntity {

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(nullable = false)
    private Float radiusX;

    @Column(nullable = false)
    private Float radiusY;

    @Column(nullable = false)
    private Float rotation;

    public EllipseItemJpaEntity(Long id, String name, HomeJpaEntity home, RoomJpaEntity room, UserJpaEntity user, List<SoundSourceJpaEntity> soundSources, Float x, Float y, Float radiusX, Float radiusY, Float rotation) {
        super(id, name, home, room, user, soundSources);
        this.x = x;
        this.y = y;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.rotation = rotation;
    }

}
