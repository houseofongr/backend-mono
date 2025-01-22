package com.hoo.aoo.admin.application.service.room;


import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoUseCase;
import com.hoo.aoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateRoomService implements UpdateRoomInfoUseCase {

    private final UpdateRoomPort updateRoomPort;

    @Override
    @Transactional
    public MessageDto update(UpdateRoomInfoCommand command) {
        int updateCount = updateRoomPort.update(command);
        return new MessageDto(updateCount + "개 룸의 정보 수정이 완료되었습니다.");
    }
}
