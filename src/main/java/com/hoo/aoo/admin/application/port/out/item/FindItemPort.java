package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.domain.item.Item;

import java.util.Optional;

public interface FindItemPort {
    Optional<Item> load(Long id);
}
