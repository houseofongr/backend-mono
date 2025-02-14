package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.RoomJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QRoomJpaEntity.roomJpaEntity;

@Slf4j
public class RoomQueryDslRepositoryImpl implements RoomQueryDslRepository {

    private final JPAQueryFactory query;

    public RoomQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<RoomJpaEntity> findByIdAndHomeIdWithItem(Long homeId, Long roomId) {

        log.warn("{} : USE this query ONLY [Read-Only Transaction]! -> ∵ Query != Database", this.getClass().getSimpleName());

        boolean roomContainsItem = query.selectOne()
                .from(roomJpaEntity)
                .innerJoin(roomJpaEntity.items,itemJpaEntity)
                .where(roomJpaEntity.id.eq(roomId)).fetchFirst() != null;

        return roomContainsItem?

                Optional.ofNullable(
                query.selectFrom(roomJpaEntity)
                        .leftJoin(roomJpaEntity.items, itemJpaEntity)
                        .fetchJoin() // WARN(fetch join with where clause)
                        .where(roomJpaEntity.id.eq(roomId)
                                .and(itemJpaEntity.home.id.eq(homeId))
                        )
                        .fetchFirst()) :

                Optional.ofNullable(
                        query.selectFrom(roomJpaEntity)
                                .where(roomJpaEntity.id.eq(roomId))
                                .fetchFirst()
        );
    }
}
