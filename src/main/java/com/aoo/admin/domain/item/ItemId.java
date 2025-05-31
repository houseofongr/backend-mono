package com.aoo.admin.domain.item;

import lombok.Getter;

@Getter
public class ItemId {
    private final Long id;

    public ItemId(Long id) {
        this.id = id;
    }
}
