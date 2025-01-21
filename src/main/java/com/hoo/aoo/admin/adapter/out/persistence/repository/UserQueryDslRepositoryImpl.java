package com.hoo.aoo.admin.adapter.out.persistence.repository;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.hoo.aoo.common.adapter.out.persistence.entity.QSnsAccountJpaEntity.snsAccountJpaEntity;
import static com.hoo.aoo.common.adapter.out.persistence.entity.QUserJpaEntity.userJpaEntity;

public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {

    private final JPAQueryFactory query;

    public UserQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<UserJpaEntity> searchByCommand(QueryUserInfoCommand command) {
        List<UserJpaEntity> entities = query.selectFrom(userJpaEntity)
                .leftJoin(userJpaEntity.snsAccountEntities, snsAccountJpaEntity)
                .orderBy(userJpaEntity.createdTime.desc())
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(userJpaEntity.count())
                .from(userJpaEntity)
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count);
    }
}
