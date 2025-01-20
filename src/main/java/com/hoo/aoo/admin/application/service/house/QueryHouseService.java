package com.hoo.aoo.admin.application.service.house;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.*;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.house.SearchHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryHouseService implements QueryHouseListUseCase, QueryHouseUseCase, QueryRoomInfoUseCase {

    private final SearchHousePort searchHousePort;
    private final FindHousePort findHousePort;
    private final FindRoomPort findRoomPort;

    @Override
    @Transactional(readOnly = true)
    public QueryHouseResult query(Long houseId) {
        HouseJpaEntity entity = findHousePort.findHouseJpaEntity(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryHouseListResult query(QueryHouseListCommand command) {
        Page<HouseJpaEntity> entities = searchHousePort.search(command);
        return new QueryHouseListResult(entities.map(QueryHouseListResult.HouseInfo::of));
    }

    @Override
    @Transactional(readOnly = true)
    public QueryRoomResult query(QueryRoomCommand command) {
        RoomJpaEntity jpaEntity = findRoomPort.findRoomJpaEntity(command.houseId())
                .orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

        return QueryRoomResult.of(jpaEntity);
    }
}
