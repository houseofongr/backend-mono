package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.in.house.DeleteHouseUseCase;
import com.hoo.aoo.admin.application.port.in.house.DeleteRoomUseCase;
import com.hoo.aoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.house.DeleteRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteHouseService implements DeleteHouseUseCase, DeleteRoomUseCase {

    private final DeleteHousePort deleteHousePort;
    private final DeleteRoomPort deleteRoomPort;

    @Override
    @Transactional
    public MessageDto deleteHouse(Long id) {
        deleteHousePort.deleteHouse(id);
        return new MessageDto(id + "번 하우스가 삭제되었습니다.");
    }

    @Override
    @Transactional
    public MessageDto deleteRoom(Long id) {
        deleteRoomPort.deleteRoom(id);
        return new MessageDto(id + "번 룸이 삭제되었습니다.");
    }
}
