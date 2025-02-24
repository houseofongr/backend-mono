package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.in.home.UpdateHomeNameUseCase;
import com.hoo.aoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.aar.application.service.AarException;
import com.hoo.aoo.admin.application.port.in.home.UpdateHomeUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateHomeNameService implements UpdateHomeNameUseCase {

    private final CheckOwnerPort checkOwnerPort;
    private final UpdateHomeUseCase updateHomeUseCase;

    @Override
    public MessageDto updateHomeName(Long userId, Long homeId, String homeName) {
        if (!checkOwnerPort.checkHome(userId, homeId)) throw new AarException(AarErrorCode.NOT_OWNED_HOME);
        updateHomeUseCase.updateHome(homeId, homeName);
        return new MessageDto(homeId + "번 홈의 이름이 수정되었습니다.");
    }
}
