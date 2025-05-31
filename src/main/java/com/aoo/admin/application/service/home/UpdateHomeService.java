package com.aoo.admin.application.service.home;

import com.aoo.admin.application.port.in.home.UpdateHomeUseCase;
import com.aoo.admin.application.port.out.home.FindHomePort;
import com.aoo.admin.application.port.out.home.UpdateHomePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.exception.BadHomeNameFormatException;
import com.aoo.admin.domain.home.Home;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateHomeService implements UpdateHomeUseCase {

    private final FindHomePort findHomePort;
    private final UpdateHomePort updateHomePort;

    @Override
    public void updateHomeName(Long homeId, String homeName) {
        Home home = findHomePort.loadHome(homeId).orElseThrow(() -> new AdminException(AdminErrorCode.HOME_NOT_FOUND));

        try {
            home.updateName(homeName);
        } catch (BadHomeNameFormatException e) {
            throw new AdminException(AdminErrorCode.ILLEGAL_HOME_NAME_FORMAT);
        }

        updateHomePort.updateHomeName(home);
    }

    @Override
    public void updateMainHome(Long userId, Long homeId) {
        updateHomePort.updateMainHome(userId, homeId);
    }
}
