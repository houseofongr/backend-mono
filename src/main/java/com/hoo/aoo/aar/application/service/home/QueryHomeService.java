package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsUseCase;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesUseCase;
import com.hoo.aoo.aar.application.port.out.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.aar.application.service.AarException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AARQueryHomeService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryHomeService implements QueryUserHomesUseCase, QueryHomeRoomsUseCase {

    private final CheckOwnerPort checkOwnerPort;
    private final QueryHomePort queryHomePort;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return queryHomePort.queryUserHomes(userId);
    }

    @Override
    public QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId) {
        if (!checkOwnerPort.checkHome(userId, homeId))
            throw new AarException(AarErrorCode.NOT_OWNED_HOME);

        return queryHomePort.queryHomeRooms(homeId);
    }
}
