package com.hoo.aoo.file.domain;

public class Owner {
    Long id;

    public Owner(Long id) {
        this.id = id;
    }

    public static Owner empty() {
        return new Owner(null);
    }
}
