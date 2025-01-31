package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.ItemJpaEntity;
import com.hoo.aoo.admin.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, Long> {
    boolean existsByRoomId(Long roomId);
    List<ItemJpaEntity> findAllByHomeId(Long homeId);
    List<ItemJpaEntity> findAllByHomeIdAndRoomId(Long homeId, Long roomId);
}
