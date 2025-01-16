package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.DeleteHouseUseCase;
import com.hoo.aoo.admin.application.port.in.DeleteRoomUseCase;
import com.hoo.aoo.admin.application.port.out.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.DeleteRoomPort;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.FindRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHouseService implements DeleteHouseUseCase, DeleteRoomUseCase {

    private final FindHousePort findHousePort;
    private final FindRoomPort findRoomPort;
    private final DeleteHousePort deleteHousePort;
    private final DeleteRoomPort deleteRoomPort;

    @Override
    public MessageDto delete(Long id) {
        return null;
    }

    @Override
    public MessageDto delete(Long houseId, String name) {
        return null;
    }
}
