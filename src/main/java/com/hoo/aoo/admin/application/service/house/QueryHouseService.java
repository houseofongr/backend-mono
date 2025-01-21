package com.hoo.aoo.admin.application.service.house;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.*;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.house.SearchHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.common.application.port.in.Pagination;
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
    public QueryHouseResult queryHouse(Long id) {
        HouseJpaEntity entity = findHousePort.findHouseJpaEntity(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return QueryHouseResult.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryHouseListResult query(QueryHouseListCommand command) {
        Page<QueryHouseListResult.HouseInfo> houseInfoPages = searchHousePort.search(command).map(QueryHouseListResult.HouseInfo::of);
        return new QueryHouseListResult(houseInfoPages.getContent(), Pagination.of(houseInfoPages));
    }

    @Override
    @Transactional(readOnly = true)
    public QueryRoomResult queryRoom(Long id) {
        RoomJpaEntity jpaEntity = findRoomPort.findRoomJpaEntity(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

        return QueryRoomResult.of(jpaEntity);
    }
}
