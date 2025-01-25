package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomJpaRepository extends JpaRepository<RoomJpaEntity, Long> {

    List<RoomJpaEntity> findAllByHouseId(Long houseId);

    void deleteAllByHouseId(Long houseId);

}
