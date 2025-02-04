package com.hoo.aoo.aar.adapter.out.persistence.mapper;

import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryItemSoundSourcesResult;
import com.hoo.aoo.aar.application.port.in.home.QueryRoomItemsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("AARHomeMapper")
public class HomeMapper {

    public QueryUserHomesResult mapToQueryUserHomes(List<HomeJpaEntity> homeJpaEntities) {
        return new QueryUserHomesResult(
                homeJpaEntities.stream().map(this::mapToHomeInfo).toList()
        );
    }

    private QueryUserHomesResult.HomeInfo mapToHomeInfo(HomeJpaEntity homeJpaEntity) {
        return new QueryUserHomesResult.HomeInfo(
                homeJpaEntity.getId(),
                homeJpaEntity.getHouse().getBasicImageFileId(),
                homeJpaEntity.getName());
    }

    public QueryHomeRoomsResult mapToQueryHomeRooms(HomeJpaEntity homeJpaEntity) {
        return new QueryHomeRoomsResult(
                homeJpaEntity.getName(),
                mapToHouseInfo(homeJpaEntity.getHouse()),
                homeJpaEntity.getHouse().getRooms().stream().map(this::mapToRoomData).toList()
        );
    }

    private QueryHomeRoomsResult.HouseInfo mapToHouseInfo(HouseJpaEntity house) {
        return new QueryHomeRoomsResult.HouseInfo(
                house.getWidth(),
                house.getHeight(),
                house.getBorderImageFileId()
        );
    }

    private QueryHomeRoomsResult.RoomData mapToRoomData(RoomJpaEntity roomJpaEntity) {
        return new QueryHomeRoomsResult.RoomData(
                roomJpaEntity.getId(),
                roomJpaEntity.getName(),
                roomJpaEntity.getX(),
                roomJpaEntity.getY(),
                roomJpaEntity.getZ(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId()
        );
    }

    public QueryRoomItemsResult mapToQueryRoomItems(RoomJpaEntity roomJpaEntity) {
        return new QueryRoomItemsResult(
                mapToRoomInfo(roomJpaEntity),
                roomJpaEntity.getItems().stream().map(this::mapToItemInfo).filter(Objects::nonNull).toList()
        );
    }

    private QueryRoomItemsResult.RoomInfo mapToRoomInfo(RoomJpaEntity roomJpaEntity) {
        return new QueryRoomItemsResult.RoomInfo(
                roomJpaEntity.getName(),
                roomJpaEntity.getWidth(),
                roomJpaEntity.getHeight(),
                roomJpaEntity.getImageFileId()
        );
    }

    private QueryRoomItemsResult.ItemData mapToItemInfo(ItemJpaEntity itemJpaEntity) {
        if (itemJpaEntity.getSoundSources().isEmpty() || itemJpaEntity.getSoundSources().stream().noneMatch(SoundSourceJpaEntity::getIsActive)) return null;

        return switch (itemJpaEntity.getShape()) {
            case ItemShapeRectangleJpaEntity rectangle -> new QueryRoomItemsResult.ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.RECTANGLE,
                    null,
                    new QueryRoomItemsResult.RectangleData(
                            rectangle.getX(),
                            rectangle.getY(),
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            rectangle.getRotation()
                    ),
                    null
            );
            case ItemShapeCircleJpaEntity circle -> new QueryRoomItemsResult.ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.RECTANGLE,
                    new QueryRoomItemsResult.CircleData(
                            circle.getX(),
                            circle.getY(),
                            circle.getRadius()
                    ),
                    null,
                    null
            );
            case ItemShapeEllipseJpaEntity ellipse -> new QueryRoomItemsResult.ItemData(
                    itemJpaEntity.getId(),
                    itemJpaEntity.getName(),
                    ItemType.RECTANGLE,
                    null,
                    null,
                    new QueryRoomItemsResult.EllipseData(
                            ellipse.getX(),
                            ellipse.getY(),
                            ellipse.getRadiusX(),
                            ellipse.getRadiusY(),
                            ellipse.getRotation()
                    )
            );
            default -> throw new IllegalStateException("Unexpected value: " + itemJpaEntity.getShape());
        };
    }

    public QueryItemSoundSourcesResult mapToQueryItemSoundSources(ItemJpaEntity itemJpaEntity) {
        return new QueryItemSoundSourcesResult(
                itemJpaEntity.getName(),
                itemJpaEntity.getSoundSources().stream().map(this::mapToSoundSourceInfo).filter(Objects::nonNull).toList()
        );
    }

    private QueryItemSoundSourcesResult.SoundSourceInfo mapToSoundSourceInfo(SoundSourceJpaEntity soundSourceJpaEntity) {

        if (!soundSourceJpaEntity.getIsActive()) return null;

        return new QueryItemSoundSourcesResult.SoundSourceInfo(
                soundSourceJpaEntity.getId(),
                soundSourceJpaEntity.getName(),
                soundSourceJpaEntity.getDescription(),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSourceJpaEntity.getCreatedTime()),
                DateTimeFormatters.DOT_DATE.getFormatter().format(soundSourceJpaEntity.getUpdatedTime())
        );
    }
}
