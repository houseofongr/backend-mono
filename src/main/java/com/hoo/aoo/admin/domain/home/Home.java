package com.hoo.aoo.admin.domain.home;

import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.admin.domain.user.UserId;
import com.hoo.aoo.common.domain.BaseTime;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Home {
    private final HomeId homeId;
    private final HouseId houseId;
    private final UserId userId;
    private final HomeDetail homeDetail;
    private final BaseTime baseTime;

    private Home(HomeId homeId, HouseId houseId, UserId userId, HomeDetail homeDetail, BaseTime baseTime) {
        this.homeId = homeId;
        this.houseId = houseId;
        this.userId = userId;
        this.homeDetail = homeDetail;
        this.baseTime = baseTime;
    }

    public static Home create(Long id, House house, User user) {
        return new Home(new HomeId(id), house.getHouseId(), user.getUserId(), new HomeDetail(house, user), null);
    }

    public static Home load(Long id, Long houseId, Long userId, String homeName, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Home(new HomeId(id), new HouseId(houseId), new UserId(userId), new HomeDetail(homeName), new BaseTime(createdTime, updatedTime));
    }
}
