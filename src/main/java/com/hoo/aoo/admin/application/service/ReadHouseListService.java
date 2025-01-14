package com.hoo.aoo.admin.application.service;


import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReadHouseListService implements ReadHouseListUseCase {



    @Override
    public ReadHouseListResult getList(ReadHouseListCommand command) {
        return null;
    }
}
