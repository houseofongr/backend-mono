package com.hoo.aoo.aar.application.service.user;

import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoUseCase;
import com.hoo.aoo.aar.application.port.out.persistence.user.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryMyInfoService implements QueryMyInfoUseCase {

    private final FindUserPort findUserPort;

    @Override
    public QueryMyInfoResult queryMyInfo(Long userId) {
        return findUserPort.queryMyInfo(userId);
    }
}
