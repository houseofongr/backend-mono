package com.aoo.aar.application.service.user;

import com.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.aoo.aar.application.port.in.user.QueryMyInfoUseCase;
import com.aoo.aar.application.port.out.persistence.user.QueryUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryMyInfoService implements QueryMyInfoUseCase {

    private final QueryUserPort queryUserPort;

    @Override
    public QueryMyInfoResult queryMyInfo(Long userId) {
        return queryUserPort.queryMyInfo(userId);
    }
}
