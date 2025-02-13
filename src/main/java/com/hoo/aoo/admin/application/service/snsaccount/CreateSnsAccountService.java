package com.hoo.aoo.admin.application.service.snsaccount;

import com.hoo.aoo.admin.application.port.in.snsaccount.CreateSnsAccountUseCase;
import com.hoo.aoo.admin.application.port.out.snsaccount.CreateSnsAccountPort;
import com.hoo.aoo.admin.application.port.out.snsaccount.SaveSnsAccountPort;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateSnsAccountService implements CreateSnsAccountUseCase {

    private final CreateSnsAccountPort createSnsAccountPort;
    private final SaveSnsAccountPort saveSnsAccountPort;

    @Override
    public SnsAccount createSnsAccount(SnsDomain domain, String snsId, String realName, String nickname, String email) {

        SnsAccount newAccount = createSnsAccountPort.createSnsAccount(SnsDomain.KAKAO, snsId, realName, nickname, email);
        saveSnsAccountPort.save(newAccount);

        return newAccount;
    }

}
