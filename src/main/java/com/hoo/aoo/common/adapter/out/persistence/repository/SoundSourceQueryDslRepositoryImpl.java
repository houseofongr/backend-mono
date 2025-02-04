package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.common.adapter.out.persistence.entity.QHomeJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.QUserJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity.soundSourceJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class SoundSourceQueryDslRepositoryImpl implements SoundSourceQueryDslRepository {

    private final JPAQueryFactory query;

    public SoundSourceQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public boolean existsByUserIdAndId(Long userId, Long soundSourceId) {
        return query.selectOne()
                .from(soundSourceJpaEntity)
                .leftJoin(soundSourceJpaEntity.item, itemJpaEntity)
                .leftJoin(itemJpaEntity.home, homeJpaEntity)
                .leftJoin(homeJpaEntity.user, userJpaEntity)
                .where(soundSourceJpaEntity.id.eq(soundSourceId)
                        .and(userJpaEntity.id.eq(userId)))
                .fetchFirst() != null;
    }
}
