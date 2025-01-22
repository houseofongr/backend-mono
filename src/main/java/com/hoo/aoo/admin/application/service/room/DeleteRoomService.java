package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.application.port.in.room.DeleteRoomUseCase;
import com.hoo.aoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteRoomService implements DeleteRoomUseCase {

    private final DeleteRoomPort deleteRoomPort;

    @Override
    @Transactional
    public MessageDto deleteRoom(Long id) {
        deleteRoomPort.deleteRoom(id);
        return new MessageDto(id + "번 룸이 삭제되었습니다.");
    }

}
