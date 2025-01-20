package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomJpaRepository extends JpaRepository<RoomJpaEntity, Long> {

    List<RoomJpaEntity> findAllByHouseId(Long houseId);

    void deleteAllByHouseId(Long houseId);

}
