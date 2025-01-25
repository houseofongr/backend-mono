package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.adapter.out.persistence.entity.ItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<ItemJpaEntity, Long> {

}
