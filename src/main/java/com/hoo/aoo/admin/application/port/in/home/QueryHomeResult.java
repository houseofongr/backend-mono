package com.hoo.aoo.admin.application.port.in.home;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;

import java.util.List;

public record QueryHomeResult(
        Long homeId,
        String homeName,
        String createdDate,
        String updatedDate,
        HouseInfo house,
        UserInfo user,
        List<RoomInfo> rooms
) {

    public static QueryHomeResult of(HomeJpaEntity homeJpaEntity, List<RoomInfo> rooms) {
        return new QueryHomeResult(
                homeJpaEntity.getId(),
                homeJpaEntity.getName(),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(homeJpaEntity.getCreatedTime()),
                DateTimeFormatters.ENGLISH_DATE.getFormatter().format(homeJpaEntity.getUpdatedTime()),
                HouseInfo.of(homeJpaEntity.getHouse()),
                UserInfo.of(homeJpaEntity.getUser()),
                rooms);
    }

    public record UserInfo(
            Long id,
            String nickname
    ) {

        public static UserInfo of(UserJpaEntity user) {
            return new UserInfo(user.getId(), user.getNickname());
        }
    }

    public record HouseInfo(
            Float width,
            Float height,
            Long borderImageId
    ) {

        public static HouseInfo of(HouseJpaEntity houseJpaEntity) {
            return new HouseInfo(
                    houseJpaEntity.getWidth(),
                    houseJpaEntity.getHeight(),
                    houseJpaEntity.getBorderImageFileId()
            );
        }

    }

    public record RoomInfo(
            Long roomId,
            String name,
            Float x,
            Float y,
            Float z,
            Float width,
            Float height,
            Long imageId
    ) {
        public static RoomInfo of(RoomJpaEntity roomJpaEntity) {
            return new RoomInfo(
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
    }
}
