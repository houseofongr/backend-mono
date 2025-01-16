package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoUseCase;
import com.hoo.aoo.admin.application.port.in.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.in.UpdateRoomInfoUseCase;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.UpdateHousePort;
import com.hoo.aoo.admin.application.port.out.UpdateRoomPort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.RoomNameDuplicatedException;
import com.hoo.aoo.admin.domain.exception.RoomNameNotFoundException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseInfoUseCase, UpdateRoomInfoUseCase {

    private final FindHousePort findHousePort;
    private final UpdateHousePort updateHousePort;
    private final UpdateRoomPort updateRoomPort;

    @Override
    @Transactional
    public MessageDto update(UpdateHouseInfoCommand command) {

        try {
            House house = findHousePort.load(command.persistenceId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            house.updateInfo(command.title(), command.author(), command.description());
            updateHousePort.update(command.persistenceId(), house);

            return new MessageDto(command.persistenceId() + "번 하우스 정보 수정이 완료되었습니다.");

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        }

    }

    @Override
    @Transactional
    public MessageDto update(UpdateRoomInfoCommand command) {
        try {
            House house = findHousePort.load(command.persistenceId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            house.updateRoomInfo(command.originalName(), command.newName());
            updateRoomPort.update(command.persistenceId(), command.originalName(), house.getRoom(command.newName()));

            return new MessageDto(command.persistenceId() + "번 하우스 " + command.originalName() + " 룸의 정보 수정이 완료되었습니다.");

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        } catch (RoomNameDuplicatedException e) {
            throw new AdminException(AdminErrorCode.ROOM_NAME_DUPLICATED);

        } catch (RoomNameNotFoundException e) {
            throw new AdminException(AdminErrorCode.ROOM_NOT_FOUND);
        }
    }
}
