package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.room.RoomId;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private final ItemId itemId;
    private final RoomId roomId;
    private final ItemDetail itemDetail;
    private Shape shape;
    private final List<SoundSource> soundSources;

    private Item(ItemId itemId, RoomId roomId, ItemDetail itemDetail, Shape shape, List<SoundSource> soundSources) {
        this.itemId = itemId;
        this.roomId = roomId;
        this.itemDetail = itemDetail;
        this.shape = shape;
        this.soundSources = soundSources;
    }

    public static Item create(Long id, Long roomId, String name, Shape shape) {
        return new Item(
                new ItemId(id),
                new RoomId(roomId),
                new ItemDetail(name),
                shape,
                List.of());
    }

    public static Item load(Long id, Long roomId, String name, Shape shape, List<SoundSource> soundSources) {
        return new Item(
                new ItemId(id),
                new RoomId(roomId),
                new ItemDetail(name),
                shape,
                soundSources
        );
    }

    public void update(String name, Shape shape) {
        if (name != null) this.itemDetail.updateName(name);
        if (shape != null) this.shape = shape;
    }

    public boolean hasSoundSource() {
        return soundSources != null && !soundSources.isEmpty();
    }
}
