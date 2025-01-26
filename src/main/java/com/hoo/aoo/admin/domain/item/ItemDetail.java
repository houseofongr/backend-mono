package com.hoo.aoo.admin.domain.item;

import lombok.Getter;

@Getter
public class ItemDetail {
    private final String name;

    public ItemDetail(String name) {
        this.name = name;
    }
}
