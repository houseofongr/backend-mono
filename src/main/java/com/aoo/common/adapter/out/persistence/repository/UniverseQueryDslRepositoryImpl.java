package com.aoo.common.adapter.out.persistence.repository;

import com.aoo.admin.application.port.in.universe.SearchUniverseCommand;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Category;
import com.aoo.common.adapter.out.persistence.condition.UniverseSearchType;
import com.aoo.common.adapter.out.persistence.condition.UniverseSortType;
import com.aoo.common.adapter.out.persistence.entity.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.aoo.common.adapter.out.persistence.entity.QElementJpaEntity.elementJpaEntity;
import static com.aoo.common.adapter.out.persistence.entity.QSpaceJpaEntity.spaceJpaEntity;
import static com.aoo.common.adapter.out.persistence.entity.QUniverseHashtagJpaEntity.universeHashtagJpaEntity;
import static com.aoo.common.adapter.out.persistence.entity.QUniverseJpaEntity.universeJpaEntity;

public class UniverseQueryDslRepositoryImpl implements UniverseQueryDslRepository {

    private final JPAQueryFactory query;

    public UniverseQueryDslRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<UniverseJpaEntity> searchAll(SearchUniverseCommand command) {
        List<UniverseJpaEntity> entities = query.selectFrom(universeJpaEntity)
                .leftJoin(universeJpaEntity.universeHashtags, universeHashtagJpaEntity).fetchJoin()
                .where(search(command))
                .where(filter(command))
                .orderBy(order(command))
                .offset(command.pageable().getOffset())
                .limit(command.pageable().getPageSize())
                .fetch();

        Long count = query.select(universeJpaEntity.count())
                .from(universeJpaEntity)
                .where(search(command))
                .where(filter(command))
                .fetchFirst();

        return new PageImpl<>(entities, command.pageable(), count == null ? 0 : count);
    }

    @Override
    public TraversalJpaEntityComponents findAllTreeComponentById(Long universeId) {
        UniverseJpaEntity universe = query
                .selectFrom(universeJpaEntity)
                .where(universeJpaEntity.id.eq(universeId))
                .stream().findFirst()
                .orElseThrow(() -> new AdminException(AdminErrorCode.UNIVERSE_NOT_FOUND));

        List<SpaceJpaEntity> spaceJpaEntities = query
                .selectFrom(spaceJpaEntity)
                .where(spaceJpaEntity.universeId.eq(universeId))
                .fetch();

        List<ElementJpaEntity> elementJpaEntities = query
                .selectFrom(elementJpaEntity)
                .where(elementJpaEntity.universeId.eq(universeId))
                .fetch();

        return new TraversalJpaEntityComponents(universe, spaceJpaEntities, elementJpaEntities);
    }

    private BooleanExpression filter(SearchUniverseCommand command) {
        if (command.category() == null || command.category().isBlank() || !Category.contains(command.category()))
            return null;

        return universeJpaEntity.category.eq(Category.valueOf(command.category().toUpperCase()));
    }

    private BooleanExpression search(SearchUniverseCommand command) {
        if (command.keyword() == null || command.keyword().isBlank() || !UniverseSearchType.contains(command.searchType()))
            return null;

        return switch (UniverseSearchType.valueOf(command.searchType().toUpperCase())) {
            case CONTENT -> universeJpaEntity.title.likeIgnoreCase("%" + command.keyword() + "%")
                    .or(universeJpaEntity.description.likeIgnoreCase("%" + command.keyword() + "%"));
            case AUTHOR -> universeJpaEntity.author.nickname.likeIgnoreCase(command.keyword());
            case ALL -> universeJpaEntity.title.likeIgnoreCase("%" + command.keyword() + "%")
                    .or(universeJpaEntity.description.likeIgnoreCase("%" + command.keyword() + "%"))
                    .or(universeJpaEntity.author.nickname.likeIgnoreCase(command.keyword()));
        };
    }

    private OrderSpecifier<?> order(SearchUniverseCommand command) {
        if (command.isAsc() == null || command.sortType() == null || !UniverseSortType.contains(command.sortType()))
            return new OrderSpecifier<>(Order.DESC, universeJpaEntity.createdTime);

        Order order = command.isAsc() ? Order.ASC : Order.DESC;
        return switch (UniverseSortType.valueOf(command.sortType().toUpperCase())) {
            case TITLE -> new OrderSpecifier<>(order, universeJpaEntity.title);
            case REGISTERED_DATE -> new OrderSpecifier<>(order, universeJpaEntity.createdTime);
            case VIEWS -> new OrderSpecifier<>(order, universeJpaEntity.viewCount);
        };
    }
}
