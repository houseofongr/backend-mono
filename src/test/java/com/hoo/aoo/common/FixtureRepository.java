package com.hoo.aoo.common;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.item.*;
import com.hoo.aoo.admin.domain.item.soundsource.SoundSource;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.MockIdAdapter;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import com.hoo.aoo.common.application.service.EntityFactoryService;
import com.nimbusds.jose.shaded.gson.Gson;

import java.time.ZonedDateTime;
import java.util.List;

public class FixtureRepository {

    private static final Gson gson = new Gson();
    private static final IssueIdPort mockIdPort = new MockIdAdapter();
    private static final EntityFactoryService entityFactoryService = new EntityFactoryService(mockIdPort);

    public static Room getRoom(String name) throws Exception {
        return Room.create(name, 0f, 0f, 0f, 1f, 1f, 1L);
    }

    public static Room getRoom() throws Exception {
        return getRoom("거실");
    }

    public static House getHouse() throws Exception {
        return getHouse(getHouseId());
    }

    public static Detail getHouseId() {
        return new Detail("cozy house", "leaf", "this is cozy house");
    }

    public static House getHouse(Detail detail) throws Exception {
        return getHouse(detail, List.of(getRoom("거실"), getRoom("주방")));
    }

    public static House getHouse(Detail detail, List<Room> rooms) throws Exception {
        return entityFactoryService.createHouse(detail, 5000f, 5000f, 1L, 1L, rooms);
    }


    private static Shape getShape(ItemType itemType) {
        switch (itemType) {
            case RECTANGLE -> {
                return new Rectangle(100f, 100f, 10f, 10f, 5f);
            }
            case CIRCLE -> {
                return new Circle(200f, 200f, 10.5f);
            }
            case ELLIPSE -> {
                return new Ellipse(500f, 500f, 15f, 15f, 90f);
            }
            default -> throw new IllegalStateException("Unexpected value: " + itemType);
        }
    }

    public static Item getItem(String itemName,ItemType itemType) {
        return Item.create(1L,itemName, getShape(itemType), List.of(getSoundSource(), getSoundSource(), getSoundSource()));
    }

    public static SoundSource getSoundSource() {
        return SoundSource.create(1L,"음원1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam nec magna imperdiet, molestie dolor fringilla, dapibus massa. Sed purus tellus, imperdiet ac lacus sed, ultrices posuere neque. Etiam sed libero sit amet urna hendrerit bibendum dignissim maximus mauris. Nulla id dolor nec orci laoreet iaculis nec quis sapien. Sed malesuada egestas diam. Donec suscipit cursus tortor, eget sollicitudin orci dignissim et. Nullam ac quam non odio imperdiet ornare. Etiam dictum felis nunc, ac sollicitudin diam scelerisque vitae. Nulla gravida viverra dignissim. Nam consequat tincidunt dolor, a egestas nulla tempor tempor. Donec placerat posuere ligula, nec sodales diam luctus ac.\n" +
                                            "\n" +
                                            "Etiam a elit venenatis, auctor justo in, rhoncus orci. Etiam vitae nisl venenatis, suscipit nibh in, porta sapien. Phasellus ultricies eget justo eget mollis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi egestas metus vulputate condimentum lacinia. Morbi velit mi, rhoncus eu nibh at, varius luctus nisi. Cras porta magna et quam mattis, ac congue nibh efficitur. Fusce sapien risus, faucibus at scelerisque non, tincidunt at nulla. Mauris euismod dapibus quam eu consequat. Aenean at felis justo. Fusce ac arcu nec elit bibendum pulvinar mattis ac erat. Donec nec venenatis orci, id volutpat mi.\n" +
                                            "\n" +
                                            "Nullam congue vulputate nisl. Pellentesque facilisis tincidunt consequat. Mauris lacinia eleifend orci, sed efficitur enim ullamcorper et. Sed facilisis metus id magna tristique, id ultricies enim auctor. Quisque sed aliquet felis, eu aliquam odio. Quisque varius tincidunt nunc quis dictum. Cras id malesuada velit. Nullam interdum, libero id finibus posuere, eros lorem imperdiet lacus, at porta metus leo in metus. Pellentesque eu commodo purus. Suspendisse at libero diam. Aenean porta non purus non suscipit.\n" +
                                            "\n" +
                                            "Donec pellentesque, felis quis elementum congue, risus nulla pulvinar purus, non egestas erat tellus vel eros. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse quis iaculis enim, ac fringilla purus. Donec ut feugiat velit. Integer malesuada placerat sagittis. Suspendisse pellentesque suscipit tristique. Aenean finibus pellentesque posuere. Nullam a varius risus. Curabitur non libero in purus fringilla posuere. Nullam massa sem, vestibulum ac posuere id, euismod at mi. Donec tempus metus commodo ex accumsan pulvinar.\n" +
                                            "\n" +
                                            "Cras dignissim justo eget mauris consectetur sollicitudin. Sed pharetra vitae nibh id congue. Curabitur elit quam, sollicitudin id justo id, rutrum fermentum nulla. Quisque eu sapien fermentum, vehicula mi sodales, rutrum sem. Aliquam erat volutpat. Donec cursus ante eu suscipit tempus. Sed cursus metus velit, vel aliquet turpis maximus eget. Nullam non risus eget metus pulvinar posuere sit amet ac erat. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent auctor dolor vel ligula sodales, eget congue sapien vehicula. Praesent venenatis sollicitudin ipsum in elementum.",
                ZonedDateTime.now(),ZonedDateTime.now(), null);
    }


    public static User getUser() {
        return User.load("leaf", "남상엽");
    }

    public static Home getHome() throws Exception {
        return Home.create(getHouse(), getUser());
    }

    public static CreateHouseMetadata getCreateHouseMetadata() {
        return new CreateHouseMetadata(
                new CreateHouseMetadata.HouseData("cozy house", "leaf", "this is cozy house.", "house", "border", 5000f, 5000f),
                List.of(
                        new CreateHouseMetadata.RoomData("room1", "거실", 123f, 456f, 1f, 100f, 200f),
                        new CreateHouseMetadata.RoomData("room2", "주방", 234f, 345f, 2f, 200f, 200f)
                )
        );
    }

    public static CreateItemMetadata getCreateItemMetadata() {
        return new CreateItemMetadata(
                List.of(
                        new CreateItemMetadata.ItemData( "강아지", ItemType.CIRCLE, new CreateItemMetadata.SoundSourceData("record1", "음원 1", "음원 1 설명", false), new CreateItemMetadata.CircleData(200f, 200f, 10.5f), null, null),
                        new CreateItemMetadata.ItemData( "설이", ItemType.RECTANGLE, new CreateItemMetadata.SoundSourceData("record2", "음원 2", "음원 2 설명", false), null, new CreateItemMetadata.RectangleData(100f, 100f, 10f, 10f, 5f), null),
                        new CreateItemMetadata.ItemData( "화분", ItemType.ELLIPSE, new CreateItemMetadata.SoundSourceData("record3", "음원 3", "음원 3 설명", false), null, null, new CreateItemMetadata.EllipseData(500f, 500f, 15f, 15f, 90f))
                )
        );
    }

    public static String getCreateHouseMetadataJson() {
        return gson.toJson(getCreateHouseMetadata());
    }

    public static String getCreateItemMetadataJson() {
        return gson.toJson(getCreateItemMetadata());
    }
}
