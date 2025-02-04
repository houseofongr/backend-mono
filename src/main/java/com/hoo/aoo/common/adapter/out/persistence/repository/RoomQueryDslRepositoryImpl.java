package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class RoomQueryDslRepositoryImpl implements RoomQueryDslRepository {

    private final JPAQueryFactory query;

    public RoomQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

}
