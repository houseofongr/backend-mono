package com.aoo.aar.application.service.home;

import com.aoo.aar.application.port.in.home.UpdateHomeNameUseCase;
import com.aoo.aar.application.port.in.home.UpdateMainHomeUseCase;
import com.aoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.aar.application.service.AarException;
import com.aoo.admin.application.port.in.home.UpdateHomeUseCase;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateMyHomeService implements UpdateHomeNameUseCase, UpdateMainHomeUseCase {

    private final CheckOwnerPort checkOwnerPort;
    private final UpdateHomeUseCase updateHomeUseCase;

    @Override
    public MessageDto updateHomeName(Long userId, Long homeId, String homeName) {
        if (!checkOwnerPort.checkHome(userId, homeId)) throw new AarException(AarErrorCode.NOT_OWNED_HOME);
        updateHomeUseCase.updateHomeName(homeId, homeName);
        return new MessageDto(homeId + "번 홈의 이름이 수정되었습니다.");
    }

    @Override
    public MessageDto updateMainHome(Long userId, Long homeId) {
        if (!checkOwnerPort.checkHome(userId, homeId)) throw new AarException(AarErrorCode.NOT_OWNED_HOME);
        updateHomeUseCase.updateMainHome(userId, homeId);
        return new MessageDto(homeId + "번 홈이 메인으로 수정되었습니다.");
    }
}
