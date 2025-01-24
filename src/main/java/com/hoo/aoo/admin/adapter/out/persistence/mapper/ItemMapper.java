package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.*;
import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.item.*;
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
                        shape.getRotation());
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
                        shape.getRadiusX(),
                        shape.getRadiusY(),
                        shape.getRotation());
            }
            default -> throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
        }
    }

    public ItemData mapToItemData(ItemJpaEntity itemJpaEntity) {
        if (itemJpaEntity instanceof RectangleItemJpaEntity rectangleItemJpaEntity) {
            return new ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.RECTANGLE,
                    null,
                    new ItemData.RectangleData(
                            rectangleItemJpaEntity.getX(),
                            rectangleItemJpaEntity.getY(),
                            rectangleItemJpaEntity.getWidth(),
                            rectangleItemJpaEntity.getHeight(),
                            rectangleItemJpaEntity.getRotation()),
                    null
            );

        } else if (itemJpaEntity instanceof CircleItemJpaEntity circleItemJpaEntity) {
            return new ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.CIRCLE,
                    new ItemData.CircleData(
                            circleItemJpaEntity.getX(),
                            circleItemJpaEntity.getY(),
                            circleItemJpaEntity.getRadius()),
                    null,
                    null
            );

        } else if (itemJpaEntity instanceof EllipseItemJpaEntity ellipseItemJpaEntity) {
            return new ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.ELLIPSE,
                    null,
                    null,
                    new ItemData.EllipseData(
                            ellipseItemJpaEntity.getX(),
                            ellipseItemJpaEntity.getY(),
                            ellipseItemJpaEntity.getRadiusX(),
                            ellipseItemJpaEntity.getRadiusY(),
                            ellipseItemJpaEntity.getRotation())
            );

        } else throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
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
