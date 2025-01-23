package com.hoo.aoo.admin.domain.item;

import lombok.Getter;

@Getter
public class ItemName {
    private final String name;

    public ItemName(String name) {
        this.name = name;
    }
}
