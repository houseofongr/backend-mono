package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeUseCase;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesUseCase;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.home.FindUserHomesPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryHomeService implements QueryHomeUseCase, QueryUserHomesUseCase {

    private final FindHomePort findHomePort;
    private final FindUserHomesPort findUserHomesPort;

    @Override
    public QueryHomeResult queryHome(Long id) {
        return findHomePort.findHome(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.HOME_NOT_FOUND));
    }

    @Override
    public QueryUserHomesResult queryUserHomes(Long id) {
        return findUserHomesPort.findUserHomes(id);
    }
}
