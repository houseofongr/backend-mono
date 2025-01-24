package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.*;
import com.hoo.aoo.admin.domain.item.Circle;
import com.hoo.aoo.admin.domain.item.Ellipse;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Rectangle;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemJpaEntity mapToNewJpaEntity(Item item, UserJpaEntity user, HomeJpaEntity home, RoomJpaEntity room) {
        switch (item.getShape().getItemType()) {
            case RECTANGLE -> {
                Rectangle shape = (Rectangle) item.getShape();
                return new RectangleItemJpaEntity(null,
                        item.getItemName().getName(),
                        home,
                        room,
                        user,
                        item.getSoundSources().stream().map(this::mapToNewJpaEntity).toList(),
                        shape.getX(),
                        shape.getY(),
                        shape.getWidth(),
                        shape.getHeight(),
                        shape.getAngle());
            }
            case CIRCLE -> {
                Circle shape = (Circle) item.getShape();
                return new CircleItemJpaEntity(null,
                        item.getItemName().getName(),
                        home,
                        room,
                        user,
                        item.getSoundSources().stream().map(this::mapToNewJpaEntity).toList(),
                        shape.getX(),
                        shape.getY(),
                        shape.getRadius());
            }
            case ELLIPSE -> {
                Ellipse shape = (Ellipse) item.getShape();
                return new EllipseItemJpaEntity(null,
                        item.getItemName().getName(),
                        home,
                        room,
                        user,
                        item.getSoundSources().stream().map(this::mapToNewJpaEntity).toList(),
                        shape.getX(),
                        shape.getY(),
                        shape.getWidth(),
                        shape.getHeight(),
                        shape.getAngle());
            }
            default -> throw new IllegalStateException("Unexpected value: " + item.getShape().getItemType());
        }
    }

    public SoundSourceJpaEntity mapToNewJpaEntity(SoundSource soundSource) {
        return new SoundSourceJpaEntity(null,
                soundSource.getDetail().getName(),
                soundSource.getDetail().getDescription(),
                soundSource.getFile().getFileId().getId(),
                soundSource.getActive().isActive(),
                null);
    }
}
