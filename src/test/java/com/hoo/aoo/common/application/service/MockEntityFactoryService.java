package com.hoo.aoo.common.application.service;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseDetail;
import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.MockIdAdapter;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MockEntityFactoryService{

    private static final EntityFactoryService factory = new EntityFactoryService(new MockIdAdapter());

    public static House getHouse() throws Exception {
        return factory.createHouse(new HouseDetail("cozy house","leaf","this is cozy house"), 5000f, 5000f, 1L, 2L, List.of(getRoom(), getRoom2()));
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

    public static User getAdminUser() {
        return User.load("leaf", "남상엽");
    }

    public static Item getRectangleItem() throws Exception{
        return factory.createItem(getRoom().getRoomId().getId(), "설이", new Rectangle(100f, 100f, 10f, 10f, 5f));
    }

    public static Item getCircleItem() throws Exception{
        return factory.createItem(getRoom().getRoomId().getId(), "강아지", new Circle(200f, 200f, 10.5f));
    }

    public static Item getEllipseItem() throws Exception{
        return factory.createItem(getRoom().getRoomId().getId(), "화분", new Ellipse(500f, 500f, 15f, 15f, 90f));
    }

    public static SoundSource getSoundSource() {
        return factory.createSoundSource(1L, "음원1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam nec magna imperdiet, molestie dolor fringilla, dapibus massa. Sed purus tellus, imperdiet ac lacus sed, ultrices posuere neque. Etiam sed libero sit amet urna hendrerit bibendum dignissim maximus mauris. Nulla id dolor nec orci laoreet iaculis nec quis sapien. Sed malesuada egestas diam. Donec suscipit cursus tortor, eget sollicitudin orci dignissim et. Nullam ac quam non odio imperdiet ornare. Etiam dictum felis nunc, ac sollicitudin diam scelerisque vitae. Nulla gravida viverra dignissim. Nam consequat tincidunt dolor, a egestas nulla tempor tempor. Donec placerat posuere ligula, nec sodales diam luctus ac. Etiam a elit venenatis, auctor justo in, rhoncus orci. Etiam vitae nisl venenatis, suscipit nibh in, porta sapien. Phasellus ultricies eget justo eget mollis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi egestas metus vulputate condimentum lacinia. Morbi velit mi, rhoncus eu nibh at, varius luctus nisi. Cras porta magna et quam mattis, ac congue nibh efficitur. Fusce sapien risus, faucibus at scelerisque non, tincidunt at nulla. Mauris euismod dapibus quam eu consequat. Aenean at felis justo. Fusce ac arcu nec elit bibendum pulvinar mattis ac erat. Donec nec venenatis orci, id volutpat mi. Nullam congue vulputate nisl. Pellentesque facilisis tincidunt consequat. Mauris lacinia eleifend orci, sed efficitur enim ullamcorper et. Sed facilisis metus id magna tristique, id ultricies enim auctor. Quisque sed aliquet felis, eu aliquam odio. Quisque varius tincidunt nunc quis dictum. Cras id malesuada velit. Nullam interdum, libero id finibus posuere, eros lorem imperdiet lacus, at porta metus leo in metus. Pellentesque eu commodo purus. Suspendisse at libero diam. Aenean porta non purus non suscipit. Donec pellentesque, felis quis elementum congue, risus nulla pulvinar purus, non egestas erat tellus vel eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse quis iaculis enim, ac fringilla purus. Donec ut feugiat velit. Integer malesuada placerat sagittis. Suspendisse pellentesque suscipit tristique. Aenean finibus pellentesque posuere. Nullam a varius risus. Curabitur non libero in purus fringilla posuere. Nullam massa sem, vestibulum ac posuere id, euismod at mi. Donec tempus metus commodo ex accumsan pulvinar. Cras dignissim justo eget mauris consectetur sollicitudin. Sed pharetra vitae nibh id congue. Curabitur elit quam, sollicitudin id justo id, rutrum fermentum nulla. Quisque eu sapien fermentum, vehicula mi sodales, rutrum sem. Aliquam erat volutpat. Donec cursus ante eu suscipit tempus. Sed cursus metus velit, vel aliquet turpis maximus eget. Nullam non risus eget metus pulvinar posuere sit amet ac erat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent auctor dolor vel ligula sodales, eget congue sapien vehicula. Praesent venenatis sollicitudin ipsum in elementum.", null);
    }

}