package com.hoo.aoo.file.domain;

import lombok.Getter;

@Getter
public class Owner {
    Long id;

    public Owner(Long id) {
        this.id = id;
    }

    public static Owner empty() {
        return new Owner(null);
    }
}
