package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.*;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.SearchHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryHouseService implements QueryHouseListUseCase, QueryHouseUseCase, QueryRoomInfoUseCase {

    private final SearchHousePort searchHousePort;
    private final FindHousePort findHousePort;

    @Override
    public QueryHouseResult query(Long houseId) {
        HouseJpaEntity entity = findHousePort.findJpaEntity(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    public QueryHouseListResult query(QueryHouseListCommand command) {
        Page<HouseJpaEntity> entities = searchHousePort.search(command);
        return new QueryHouseListResult(entities.map(QueryHouseListResult.House::of));
    }

    @Override
    public QueryRoomResult query(QueryRoomCommand command) {
        return null;
    }
}
