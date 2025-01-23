package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.item.soundsource.SoundSource;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private final ItemId itemId;
    private final Shape shape;
    private final List<SoundSource> soundSources;

    private Item(ItemId itemId, Shape shape, List<SoundSource> soundSources) {
        this.itemId = itemId;
        this.shape = shape;
        this.soundSources = soundSources;
    }

    public static Item create(Long roomId, String itemName, Shape shape, List<SoundSource> soundSources) {
        return new Item(new ItemId(roomId, itemName), shape, soundSources);
    }
}
