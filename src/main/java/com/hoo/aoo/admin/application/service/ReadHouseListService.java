package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseListUseCase;
import com.hoo.aoo.admin.application.port.out.database.QueryHousePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadHouseListService implements ReadHouseListUseCase {

    private final QueryHousePort queryHousePort;

    @Override
    public ReadHouseListResult getList(ReadHouseListCommand command) {
        List<HouseJpaEntity> entities = queryHousePort.query(command);
        return new ReadHouseListResult(entities.stream().map(ReadHouseListResult.House::of).toList());
    }
}
