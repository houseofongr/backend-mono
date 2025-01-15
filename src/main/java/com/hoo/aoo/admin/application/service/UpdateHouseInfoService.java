package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoUseCase;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateHouseInfoService implements UpdateHouseInfoUseCase {

    private final LoadHousePort loadHousePort;
    private final UpdateHousePort updateHousePort;

    @Override
    @Transactional
    public MessageDto update(UpdateHouseInfoCommand command) {

        try {
            House house = loadHousePort.load(command.persistenceId())
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
}
