package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QHouseJpaEntity.houseJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QRoomJpaEntity.roomJpaEntity;

public class HomeQueryDslRepositoryImpl implements HomeQueryDslRepository {

    private final JPAQueryFactory query;

    public HomeQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Optional<HomeJpaEntity> findByIdWithHouseAndRooms(Long id) {
        return Optional.ofNullable(
                query.selectFrom(homeJpaEntity)
                        .leftJoin(homeJpaEntity.house, houseJpaEntity).fetchJoin()
                        .leftJoin(houseJpaEntity.rooms, roomJpaEntity).fetchJoin()
                        .where(homeJpaEntity.id.eq(id))
                        .fetchFirst());
    }
}
