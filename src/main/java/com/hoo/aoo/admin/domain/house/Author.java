package com.hoo.aoo.admin.domain.house;

import lombok.Getter;

@Getter
public class Author {

    private final String name;

    public Author(String name) {
        this.name = name;
    }
}
