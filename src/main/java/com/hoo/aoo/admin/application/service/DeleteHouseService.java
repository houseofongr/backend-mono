package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.house.DeleteHouseUseCase;
import com.hoo.aoo.admin.application.port.in.house.DeleteRoomUseCase;
import com.hoo.aoo.admin.application.port.out.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.DeleteRoomPort;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.FindRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteHouseService implements DeleteHouseUseCase, DeleteRoomUseCase {

    private final FindHousePort findHousePort;
    private final FindRoomPort findRoomPort;
    private final DeleteHousePort deleteHousePort;
    private final DeleteRoomPort deleteRoomPort;

    @Override
    @Transactional
    public MessageDto delete(Long id) {
        deleteHousePort.delete(id);
        return new MessageDto(id + "번 하우스가 삭제되었습니다.");
    }

    @Override
    @Transactional
    public MessageDto delete(Long houseId, String roomName) {
        deleteRoomPort.delete(houseId, roomName);
        return new MessageDto(houseId + "번 하우스 " + roomName + " 룸이 삭제되었습니다.");
    }
}
