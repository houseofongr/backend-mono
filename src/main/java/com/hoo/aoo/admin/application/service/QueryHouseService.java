package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.*;
import com.hoo.aoo.admin.application.port.out.SearchHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryHouseService implements QueryHouseListUseCase, QueryHouseUseCase {

    private final SearchHousePort searchHousePort;

    @Override
    public QueryHouseResult get(Long houseId) {
        HouseJpaEntity entity = searchHousePort.query(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    public QueryHouseListResult getList(QueryHouseListCommand command) {
        Page<HouseJpaEntity> entities = searchHousePort.pageQuery(command);
        return new QueryHouseListResult(entities.map(QueryHouseListResult.House::of));
    }
}
