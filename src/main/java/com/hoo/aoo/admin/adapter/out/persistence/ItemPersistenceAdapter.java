package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements SaveItemPort {
    @Override
    public List<Long> save(List<Item> items) {
        return List.of();
    }
}
