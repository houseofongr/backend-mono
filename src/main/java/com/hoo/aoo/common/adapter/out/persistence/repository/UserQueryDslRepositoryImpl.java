package com.hoo.aoo.common.adapter.out.persistence.repository;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.common.adapter.out.persistence.entity.QHomeJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QHomeJpaEntity.homeJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QItemJpaEntity.itemJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QSnsAccountJpaEntity.snsAccountJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QSoundSourceJpaEntity.soundSourceJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    private final JPAQueryFactory query;

    public UserQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<UserJpaEntity> searchByCommand(QueryUserInfoCommand command) {
        List<UserJpaEntity> entities = query.selectFrom(userJpaEntity)
                .leftJoin(userJpaEntity.snsAccountEntities, snsAccountJpaEntity).fetchJoin()
                .orderBy(userJpaEntity.createdTime.desc())
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(userJpaEntity.count())
                .from(userJpaEntity)
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count);
    }

    @Override
    public Integer countHomeCountById(Long userId) {
        Long count = query.select(homeJpaEntity.count())
                .from(homeJpaEntity)
                .join(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId))
                .fetchFirst();

        return count == null? 0 : Math.toIntExact(count);
    }

    @Override
    public Integer countActiveSoundSourceCountById(Long userId) {
        Long count = query.select(soundSourceJpaEntity.count())
                .from(soundSourceJpaEntity)
                .join(soundSourceJpaEntity.item, itemJpaEntity)
                .join(itemJpaEntity.home, homeJpaEntity)
                .join(homeJpaEntity.user, userJpaEntity)
                .where(userJpaEntity.id.eq(userId)
                        .and(soundSourceJpaEntity.isActive.isTrue()))
                .fetchFirst();

        return count == null? 0 : Math.toIntExact(count);
    }
}
