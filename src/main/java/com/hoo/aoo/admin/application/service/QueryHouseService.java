package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.*;
import com.hoo.aoo.admin.application.port.out.QueryHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryHouseService implements QueryHouseListUseCase, QueryHouseUseCase {

    private final QueryHousePort queryHousePort;

    @Override
    public QueryHouseResult get(Long houseId) {
        HouseJpaEntity entity = queryHousePort.query(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    public QueryHouseListResult getList(QueryHouseListCommand command) {
        Page<HouseJpaEntity> entities = queryHousePort.pageQuery(command);
        return new QueryHouseListResult(entities.map(QueryHouseListResult.House::of));
    }
}
