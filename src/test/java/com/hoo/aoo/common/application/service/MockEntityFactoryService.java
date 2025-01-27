package com.hoo.aoo.common.application.service;

import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseDetail;
import com.hoo.aoo.admin.domain.item.Circle;
import com.hoo.aoo.admin.domain.item.Ellipse;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Rectangle;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.common.adapter.MockIdAdapter;

import java.time.ZonedDateTime;
import java.util.List;

public class MockEntityFactoryService {

    private static final EntityFactoryService factory = new EntityFactoryService(new MockIdAdapter());

    public static SnsAccount getSnsAccount() {
        return factory.createSnsAccount(SnsDomain.KAKAO, "SNS_ID", "남상엽", "leaf", "test@example.com");
    }

    public static User getUser() {
        return factory.createUser(getSnsAccount(), true, true);
    }

    public static House getHouse() throws Exception {
        return factory.createHouse(new HouseDetail("cozy house", "leaf", "this is cozy house"), 5000f, 5000f, 1L, 2L, List.of(getRoom(), getRoom2()));
    }

    public static Room getRoom() throws Exception {
        return factory.createRoom("거실", 0f, 0f, 0f, 5000f, 1000f, 3L);
    }

    public static Room getRoom2() throws Exception {
        return factory.createRoom("주방", 0f, 1000f, 0f, 5000f, 1000f, 4L);
    }

    public static Home getHome() throws Exception {
        return factory.createHome(getHouse(), getAdminUser());
    }

    public static com.hoo.aoo.admin.domain.user.User getAdminUser() {
        return com.hoo.aoo.admin.domain.user.User.load(10L, "leaf", "남상엽");
    }

    public static Item getRectangleItem() throws Exception {
        return factory.createItem(getRoom().getRoomId().getId(), getAdminUser().getUserId().getId(), "설이", new Rectangle(100f, 100f, 10f, 10f, 5f));
    }

    public static Item getCircleItem() throws Exception {
        return factory.createItem(getRoom().getRoomId().getId(), getAdminUser().getUserId().getId(), "강아지", new Circle(200f, 200f, 10.5f));
    }

    public static Item getEllipseItem() throws Exception {
        return factory.createItem(getRoom().getRoomId().getId(), getAdminUser().getUserId().getId(), "화분", new Ellipse(500f, 500f, 15f, 15f, 90f));
    }

    public static SoundSource getSoundSource() {
        return factory.createSoundSource(1L, "골골송", "2025년 설이가 보내는 골골송", null);
    }

    public static Item loadRectangleItem() throws Exception {
        Item rectangleItem = getRectangleItem();
        return Item.load(rectangleItem.getItemId().getId(), rectangleItem.getUserId().getId(), rectangleItem.getRoomId().getId(), rectangleItem.getItemDetail().getName(), rectangleItem.getShape(), List.of(getSoundSource()));
    }

    public static SoundSource loadSoundSource() {
        SoundSource soundSource = getSoundSource();
        return SoundSource.load(soundSource.getSoundSourceId().getId(), soundSource.getFile().getFileId().getId(), soundSource.getSoundSourceDetail().getName(),soundSource.getSoundSourceDetail().getDescription(), ZonedDateTime.now(),ZonedDateTime.now(),soundSource.getActive().isActive());
    }

}