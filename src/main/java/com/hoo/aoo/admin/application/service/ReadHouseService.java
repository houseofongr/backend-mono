package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseListUseCase;
import com.hoo.aoo.admin.application.port.in.ReadHouseUseCase;
import com.hoo.aoo.admin.application.port.out.database.QueryHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadHouseService implements ReadHouseListUseCase, ReadHouseUseCase {

    private final QueryHousePort queryHousePort;

    @Override
    public ReadHouseListResult get(Long houseId) {
        return null;
    }

    @Override
    public ReadHouseListResult getList(ReadHouseListCommand command) {
        Page<HouseJpaEntity> entities = queryHousePort.pageQuery(command);
        return new ReadHouseListResult(entities.map(ReadHouseListResult.House::of));
    }
}
