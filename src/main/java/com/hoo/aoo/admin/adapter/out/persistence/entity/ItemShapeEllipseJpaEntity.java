package com.hoo.aoo.admin.adapter.out.persistence.entity;

import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ITEM_SHAPE_ELLIPSE")
@DiscriminatorValue("ELLIPSE")
@Getter
@NoArgsConstructor
public class ItemShapeEllipseJpaEntity extends ItemShapeJpaEntity {

    @Column(nullable = false)
    private Float radiusX;

    @Column(nullable = false)
    private Float radiusY;

    @Column(nullable = false)
    private Float rotation;

    public ItemShapeEllipseJpaEntity(Long id, Float x, Float y, Float radiusX, Float radiusY, Float rotation) {
        super(id, x, y, null);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.rotation = rotation;
    }

}
