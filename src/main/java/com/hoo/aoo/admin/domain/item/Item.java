package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.soundsource.SoundSource;
import com.hoo.aoo.admin.domain.room.RoomId;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private final ItemId itemId;
    private final RoomId roomId;
    private final ItemName itemName;
    private final Shape shape;
    private final List<SoundSource> soundSources;

    private Item(ItemId itemId, RoomId roomId, ItemName itemName, Shape shape, List<SoundSource> soundSources) {
        this.itemId = itemId;
        this.roomId = roomId;
        this.itemName = itemName;
        this.shape = shape;
        this.soundSources = soundSources;
    }

    public static Item create(Long id, Long roomId, String name, Shape shape) {
        return new Item(
                new ItemId(id),
                new RoomId(roomId),
                new ItemName(name),
                shape,
                List.of());
    }
}
