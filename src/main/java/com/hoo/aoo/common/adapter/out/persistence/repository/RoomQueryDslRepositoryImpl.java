package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QRoomJpaEntity.roomJpaEntity;

public class RoomQueryDslRepositoryImpl implements RoomQueryDslRepository {

    private final JPAQueryFactory query;

    public RoomQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<RoomJpaEntity> findByIdAndHomeIdWithItem(Long homeId, Long roomId) {
        return Optional.ofNullable(
                query.selectFrom(roomJpaEntity)
                        .leftJoin(roomJpaEntity.items, itemJpaEntity)
                        .fetchJoin()
                        .where(roomJpaEntity.id.eq(roomId)
                                .and(itemJpaEntity.home.id.eq(homeId))
                        )
                        .fetchFirst()
        );
    }
}
