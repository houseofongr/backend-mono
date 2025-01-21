package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeUseCase;
import com.hoo.aoo.admin.application.port.out.home.SaveHomePort;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateHomeService implements CreateHomeUseCase {

    private final FindHousePort findHousePort;
    private final FindUserPort findUserPort;
    private final SaveHomePort saveHomePort;

    @Override
    @Transactional
    public CreateHomeResult create(CreateHomeCommand command) {
        try {
            House house = findHousePort.find(command.houseId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.HOUSE_NOT_FOUND));

            User user = findUserPort.find(command.userId())
                    .orElseThrow(() -> new AdminException(AdminErrorCode.USER_NOT_FOUND));

            Home home = Home.create(house, user);

            return saveHomePort.save(command, home);

        } catch (AreaLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AREA_SIZE_LIMIT_EXCEED);

        } catch (AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.AXIS_PIXEL_LIMIT_EXCEED);

        } catch (HouseRelationshipException e) {
            throw new AdminException(AdminErrorCode.ILLEGAL_HOUSE_RELATIONSHIP);

        }
    }
}
