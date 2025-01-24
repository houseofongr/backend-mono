package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Shape;
import com.hoo.aoo.admin.domain.soundsource.SoundSource;

import java.util.List;

public interface CreateItemPort {
    Item createItem(Long roomId, String name, Shape shape);
}
