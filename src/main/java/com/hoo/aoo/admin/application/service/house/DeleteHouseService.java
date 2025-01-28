package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.in.house.DeleteHouseUseCase;
import com.hoo.aoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteHouseService implements DeleteHouseUseCase {

    private final FindHousePort findHousePort;
    private final DeleteHousePort deleteHousePort;

    @Override
    @Transactional
    public MessageDto deleteHouse(Long id) {
        try {

            House house = findHousePort.load(id)
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            deleteHousePort.deleteHouse(house.getHouseId().getId());

            return new MessageDto(id + "번 하우스가 삭제되었습니다.");

        } catch (AreaLimitExceededException | AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.LOAD_ENTITY_FAILED);

        }
    }
}
