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
@Table(name = "ITEM_SHAPE_CIRCLE")
@DiscriminatorValue("CIRCLE")
@Getter
@NoArgsConstructor
public class ItemShapeCircleJpaEntity extends ItemShapeJpaEntity {

    @Column(nullable = false)
    private Float radius;

    public ItemShapeCircleJpaEntity(Long id, Float x, Float y, Float radius) {
        super(id, x, y, null);
        this.radius = radius;
    }
}
