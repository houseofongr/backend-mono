package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryItemSoundSourcesResult;
import com.hoo.aoo.aar.application.port.in.home.QueryRoomItemsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.aar.application.port.out.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.ItemJpaRepository;
import com.hoo.aoo.common.adapter.out.persistence.repository.RoomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AARHomePersistenceAdapter")
@RequiredArgsConstructor
public class HomePersistenceAdapter implements QueryHomePort, CheckOwnerPort {

    private final HomeJpaRepository homeJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final ItemJpaRepository itemJpaRepository;
    private final HomeMapper homeMapper;

    @Override
    public QueryUserHomesResult queryUserHomes(Long userId) {
        return homeMapper.mapToQueryUserHomes(homeJpaRepository.findAllByUserId(userId));
    }

    @Override
    public QueryHomeRoomsResult queryHomeRooms(Long homeId) {
        return homeMapper.mapToQueryHomeRooms(homeJpaRepository.findById(homeId).orElseThrow());
    }

    @Override
    public QueryRoomItemsResult queryRoomItems(Long roomId) {
        return homeMapper.mapToQueryRoomItems(roomJpaRepository.findById(roomId).orElseThrow());
    }

    @Override
    public QueryItemSoundSourcesResult queryItemSoundSources(Long itemId) {
        return homeMapper.mapToQueryItemSoundSources(itemJpaRepository.findById(itemId).orElseThrow());
    }

    @Override
    public boolean checkHome(Long userId, Long homeId) {
        return homeJpaRepository.existsByUserIdAndId(userId, homeId);
    }

    @Override
    public boolean checkRoom(Long homeId, Long roomId) {
        Long houseId = homeJpaRepository.findById(homeId).orElseThrow().getHouse().getId();
        return roomJpaRepository.existsByHouseIdAndId(houseId, roomId);
    }

    @Override
    public boolean checkItem(Long homeId, Long itemId) {
        return itemJpaRepository.existsByHomeIdAndId(homeId, itemId);
    }
}
