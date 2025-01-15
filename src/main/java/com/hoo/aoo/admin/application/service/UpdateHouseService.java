package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseCommand;
import com.hoo.aoo.admin.application.port.in.UpdateHouseUseCase;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseUseCase {

    private final LoadHousePort loadHousePort;
    private final UpdateHousePort updateHousePort;

    @Override
    @Transactional
    public MessageDto update(UpdateHouseCommand command) {

        return null;

    }
}
