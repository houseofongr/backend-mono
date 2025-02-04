package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.in.home.*;
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
public class QueryHomeService implements QueryUserHomesUseCase, QueryHomeRoomsUseCase, QueryRoomItemsUseCase, QueryItemSoundSourceUseCase {

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

    @Override
    public QueryRoomItemsResult queryRoomItems(Long userId, Long homeId, Long roomId) {
        if (!checkOwnerPort.checkHome(userId, homeId))
            throw new AarException(AarErrorCode.NOT_OWNED_HOME);

        if (!checkOwnerPort.checkRoom(homeId, roomId))
            throw new AarException(AarErrorCode.NOT_OWNED_ROOM);

        return queryHomePort.queryRoomItems(roomId);
    }

    @Override
    public QueryItemSoundSourcesResult queryItemSoundSources(Long userId, Long homeId, Long itemId) {
        if (!checkOwnerPort.checkHome(userId, homeId))
            throw new AarException(AarErrorCode.NOT_OWNED_HOME);

        if (!checkOwnerPort.checkItem(homeId, itemId))
            throw new AarException(AarErrorCode.NOT_OWNED_ITEM);

        return queryHomePort.queryItemSoundSources(itemId);
    }
}
