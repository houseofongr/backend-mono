package com.aoo.admin.application.service.snsaccount;

import com.aoo.admin.application.port.in.snsaccount.LoadSnsAccountUseCase;
import com.aoo.admin.application.port.out.snsaccount.FindSnsAccountPort;
import com.aoo.admin.domain.user.snsaccount.SnsAccount;
import com.aoo.admin.domain.user.snsaccount.SnsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoadSnsAccountService implements LoadSnsAccountUseCase {

    private final FindSnsAccountPort findSnsAccountPort;

    @Override
    public SnsAccount loadSnsAccount(SnsDomain domain, String snsId) {
        return findSnsAccountPort.loadSnsAccount(domain, snsId).orElse(null);
    }
}
