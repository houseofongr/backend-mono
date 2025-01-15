package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.*;
import com.hoo.aoo.admin.application.port.out.database.QueryHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadHouseService implements ReadHouseListUseCase, ReadHouseUseCase {

    private final QueryHousePort queryHousePort;

    @Override
    public ReadHouseResult get(Long houseId) {
        HouseJpaEntity entity = queryHousePort.query(houseId)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

        return ReadHouseResult.of(entity);
    }

    @Override
    public ReadHouseListResult getList(ReadHouseListCommand command) {
        Page<HouseJpaEntity> entities = queryHousePort.pageQuery(command);
        return new ReadHouseListResult(entities.map(ReadHouseListResult.House::of));
    }
}
