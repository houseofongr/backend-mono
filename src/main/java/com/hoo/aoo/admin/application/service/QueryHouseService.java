package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.*;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.SearchHousePort;
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
        HouseJpaEntity entity = findHousePort.findJpaEntity(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryHouseListResult query(QueryHouseListCommand command) {
        Page<HouseJpaEntity> entities = searchHousePort.search(command);
        return new QueryHouseListResult(entities.map(QueryHouseListResult.House::of));
    }

    @Override
    @Transactional(readOnly = true)
    public QueryRoomResult query(QueryRoomCommand command) {
        RoomJpaEntity jpaEntity = findRoomPort.findJpaEntity(command.houseId(), command.roomName())
                .orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

        return QueryRoomResult.of(jpaEntity);
    }
}
