package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.in.house.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.in.house.UpdateHouseInfoUseCase;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateHouseService implements UpdateHouseInfoUseCase {

    private final FindHousePort findHousePort;
    private final UpdateHousePort updateHousePort;

    @Override
    @Transactional
    public MessageDto update(UpdateHouseInfoCommand command) {

        try {
            House house = findHousePort.load(command.persistenceId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            house.updateDetail(command.title(), command.author(), command.description());
            updateHousePort.update(house);

            return new MessageDto(command.persistenceId() + "번 하우스 정보 수정이 완료되었습니다.");

        } catch (AxisLimitExceededException | AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.LOAD_ENTITY_FAILED);

        }
    }

}
