package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.*;
import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final SoundSourceMapper soundSourceMapper;

    public Item mapToDomainEntity(ItemJpaEntity itemJpaEntity) {
        return switch (itemJpaEntity) {
            case RectangleItemJpaEntity rectangleItemJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getName(),
                    new Rectangle(rectangleItemJpaEntity.getX(),
                            rectangleItemJpaEntity.getY(),
                            rectangleItemJpaEntity.getWidth(),
                            rectangleItemJpaEntity.getHeight(),
                            rectangleItemJpaEntity.getRotation()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case CircleItemJpaEntity circleItemJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getName(),
                    new Circle(circleItemJpaEntity.getX(),
                            circleItemJpaEntity.getY(),
                            circleItemJpaEntity.getRadius()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case EllipseItemJpaEntity ellipseItemJpaEntity -> Item.load(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getRoom().getId(),
                    itemJpaEntity.getName(),
                    new Ellipse(ellipseItemJpaEntity.getX(),
                            ellipseItemJpaEntity.getY(),
                            ellipseItemJpaEntity.getRadiusX(),
                            ellipseItemJpaEntity.getRadiusY(),
                            ellipseItemJpaEntity.getRotation()),
                    itemJpaEntity.getSoundSources().stream().map(soundSourceMapper::mapToDomainEntity).toList()
            );
            case null, default -> throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
        };
    }

    public ItemJpaEntity mapToNewJpaEntity(Item item, UserJpaEntity user, HomeJpaEntity home, RoomJpaEntity room) {
        switch (item.getShape().getItemType()) {
            case RECTANGLE -> {
                Rectangle shape = (Rectangle) item.getShape();
                return new RectangleItemJpaEntity(null,
                        item.getItemDetail().getName(),
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
                        item.getItemDetail().getName(),
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
                        item.getItemDetail().getName(),
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
        return switch (itemJpaEntity) {
            case RectangleItemJpaEntity rectangleItemJpaEntity -> new ItemData(
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
            case CircleItemJpaEntity circleItemJpaEntity -> new ItemData(
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
            case EllipseItemJpaEntity ellipseItemJpaEntity -> new ItemData(
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
            case null, default -> throw new AdminException(AdminErrorCode.ILLEGAL_SHAPE_TYPE);
        };
    }

    public SoundSourceJpaEntity mapToNewJpaEntity(SoundSource soundSource) {
        return new SoundSourceJpaEntity(null,
                soundSource.getSoundSourceDetail().getName(),
                soundSource.getSoundSourceDetail().getDescription(),
                soundSource.getFile().getFileId().getId(),
                soundSource.getActive().isActive(),
                null);
    }
}
