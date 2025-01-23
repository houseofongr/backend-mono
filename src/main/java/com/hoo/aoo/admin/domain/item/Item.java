package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.file.FileId;
import lombok.Getter;

import java.util.List;

@Getter
public class Item {
    private final ItemId itemId;
    private final ItemName itemName;
    private final Shape shape;
    private final List<FileId> audioFileIds;

    private Item(ItemId itemId, ItemName itemName, Shape shape, List<FileId> audioFileIds) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.shape = shape;
        this.audioFileIds = audioFileIds;
    }

    public static Item create(Long itemId, String itemName, Shape shape, List<Long> audioFileIds) {
        List<FileId> itemIds = audioFileIds.stream().map(FileId::new).toList();

        return new Item(new ItemId(itemId), new ItemName(itemName), shape, itemIds);
    }
}
