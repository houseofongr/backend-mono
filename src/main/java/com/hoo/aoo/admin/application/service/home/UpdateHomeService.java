package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.UpdateHomeUseCase;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.home.UpdateHomePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.BadHomeNameFormatException;
import com.hoo.aoo.admin.domain.home.Home;
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
    public void updateHome(Long homeId, String homeName) {
        Home home = findHomePort.loadHome(homeId).orElseThrow(() -> new AdminException(AdminErrorCode.HOME_NOT_FOUND));

        try {
            home.updateName(homeName);
        } catch (BadHomeNameFormatException e) {
            throw new AdminException(AdminErrorCode.ILLEGAL_HOME_NAME_FORMAT);
        }

        updateHomePort.updateHome(home);
    }
}
