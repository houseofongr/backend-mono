package com.hoo.aoo.admin.application.port.in.home;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;

import java.util.List;

public record QueryUserHomesResult(
        List<HomeInfo> homes
) {
    public record HomeInfo(
            Long id,
            String name,
            String createdDate,
            String updatedDate,
            UserInfo user,
            HouseInfo baseHouse
    ) {
        public static HomeInfo of(HomeJpaEntity homeJpaEntity) {
            return new HomeInfo(
                    homeJpaEntity.getId(),
                    homeJpaEntity.getName(),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(homeJpaEntity.getCreatedTime()),
                    DateTimeFormatters.DOT_DATE.getFormatter().format(homeJpaEntity.getUpdatedTime()),
                    UserInfo.of(homeJpaEntity.getUser()),
                    HouseInfo.of(homeJpaEntity.getHouse())
            );
        }
    }

    public record UserInfo(
            Long id,
            String nickname
    ) {

        public static UserInfo of(UserJpaEntity userJpaEntity) {
            return new UserInfo(userJpaEntity.getId(), userJpaEntity.getNickname());
        }
    }

    public record HouseInfo(
            Long id,
            String title,
            String author,
            String description
    ) {
        public static HouseInfo of(HouseJpaEntity houseJpaEntity) {
            return new HouseInfo(
                    houseJpaEntity.getId(),
                    houseJpaEntity.getTitle(),
                    houseJpaEntity.getAuthor(),
                    houseJpaEntity.getDescription().length() > 100 ? houseJpaEntity.getDescription().substring(0, 100) + "..." : houseJpaEntity.getDescription()
            );
        }
    }
}
