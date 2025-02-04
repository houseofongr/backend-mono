package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesUseCase;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AARQueryHomeService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryHomeService implements QueryUserHomesUseCase {

    private final QueryHomePort queryHomePort;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return queryHomePort.queryUserHome(userId);
    }
}
