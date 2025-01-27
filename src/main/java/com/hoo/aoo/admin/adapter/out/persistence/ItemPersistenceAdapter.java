package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SoundSourceMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.out.item.DeleteItemPort;
import com.hoo.aoo.admin.application.port.out.item.FindItemPort;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.item.UpdateItemPort;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter implements SaveItemPort, FindItemPort, UpdateItemPort, DeleteItemPort {

    private final HomeJpaRepository homeJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final ItemMapper itemMapper;
    private final SoundSourceMapper soundSourceMapper;

    @Override
    public List<Long> save(Long userId, Long homeId, Long roomId, List<Item> items) {

        UserJpaEntity userJpaEntity = userJpaRepository.findById(userId).orElseThrow();
        RoomJpaEntity roomJpaEntity = roomJpaRepository.findById(roomId).orElseThrow();

        List<ItemJpaEntity> itemJpaEntities = items.stream().map(ItemJpaEntity::create).toList();
        itemJpaEntities.forEach(itemJpaEntity -> itemJpaEntity.setRelationship(userJpaEntity, roomJpaEntity));

        itemJpaRepository.saveAll(itemJpaEntities);

        return itemJpaEntities.stream().map(ItemJpaEntity::getId).toList();
    }

    @Override
    public Optional<Item> load(Long id) {
        return itemJpaRepository.findById(id)
                .map(itemMapper::mapToDomainEntity);
    }

    @Override
    public void updateItem(Item item) {
        ItemJpaEntity itemJpaEntity = itemJpaRepository.findById(item.getItemId().getId()).orElseThrow();
        itemJpaEntity.update(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemJpaRepository.deleteById(id);
    }
}
